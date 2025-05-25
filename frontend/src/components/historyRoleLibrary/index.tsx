import { useEffect, useState, useRef } from 'react'
import { Modal, Select, Input } from 'antd';
import { searchMyPriActors } from '@/api/RoleMangerApi'
import { format } from 'date-fns';
import ImageSelect  from  '../imageSelect/index'
import Highlight from '../hightlight/Highlight'
import './index.less'
import endIcon from '@/assets/无数据.png'



function HistoryRoleLibrary({ title, storyId, isModalVisible, handleModalCancel, ensureSelectRole }) {
    const { Search } = Input;
  
    const [historyList, setHistoryList] = useState([])
    const [searchText, setSearchText] = useState('')
    const [searchValue, setSearchValue] = useState('')
    
    const [currentTag, setCurrentTag] =useState({})

    const scrollContainerRef = useRef(null);  


    const [currentPage, setCurrentPage] = useState(1);
    const [isLoading, setIsLoading] = useState(false);  
    const [isEnd, setIsEnd] = useState(false);  
    const [searchContentTypes, setSearchContentTypes] = useState([
        {
            value: '2',
            label: '故事名',
        },
        {
            value: '1',
            label: '角色名',
        }
    ])
    const handleScroll = () => {
        const { scrollTop, scrollHeight, clientHeight } = scrollContainerRef.current;
        if (scrollTop + clientHeight >= scrollHeight - 100) {
            setIsLoading(true)
           setTimeout(()=>{
            setIsLoading(false)
            getHistoryActors("")
           },2000)
        }
    }; 
    useEffect(() => {
      
        if (scrollContainerRef.current) {
            scrollContainerRef.current.addEventListener('scroll', handleScroll);
        }

        return () => {
            if (scrollContainerRef.current) {
                scrollContainerRef.current.removeEventListener('scroll', handleScroll);
            }
        };  
    }, [currentPage, isLoading]); // 依赖项，确保只在需要时重新监听 
    const [orderTypes, setOrderTypes] = useState([
        {
            value: 'asc',
            label: '时间正序',
        },
        {
            value: 'desc',
            label: '时间反序',
        }
    ])
    const [searchType, setSearchType] = useState(2)
    const [sortType, setSortType] = useState('asc')

    const searchContentChange=(value)=>{
        setHistoryList([])   
       setSearchType(value)
       getHistoryActors(searchValue,1,value)

    }
    const orderTypeChange=(value)=>{
        setHistoryList([])   
        setSortType(value)
        getHistoryActors(searchValue, 1, value)

    }
   
    const onSearch = (value) =>{
        setIsEnd(false)
        setHistoryList([])  
        setSearchText(value)
        setCurrentPage(1)
        getHistoryActors(value,1)
       
    }
   
    useEffect(() => {
        setCurrentPage(1)
        setHistoryList([])  
        setSearchText('')
        setSearchValue("")
        getHistoryActors("",1) 
      

      
    }, [isModalVisible])
    async function getHistoryActors(keyWord, page = -1, type = -1, sort=''){
        if (isLoading||isEnd) return;
        setIsLoading(true); 
        if(page==-1){
            page =currentPage

        } 
        if (type ==-1){
            type =searchType

        }
        if(sort==''){
            sort =sortType
        }

        let res = await searchMyPriActors({
            "type": type,
            "orderByWhat":sort,
            "keywords": keyWord,
            "page":page,
            "pageSize":5
        })
        console.log(res)
        if (res.records == null || res.records ==undefined||res.records.length==0){
            setIsEnd(true)

        }
        setHistoryList(prevData => [...prevData, ...res.records])
        setCurrentPage(prevPage => prevPage + 1);
        setIsLoading(false);     
    } 
    //选择
    function selectType(item: {}) {
        setCurrentTag(item)
        
    }
   //确定选择
   function  selectPrivateRole(){
      ensureSelectRole(currentTag,2)
    }
    return (
        <>
            <Modal
                title={title}
                open={isModalVisible}
                onCancel={handleModalCancel}
                okText="确定"
                onOk={selectPrivateRole}
                cancelText="取消"
                mask={false}
                centered
              
            >
                <div >
                    <Select
                        defaultValue="故事名"
                        style={{ width: 88 }}
                        options={searchContentTypes}
                        onChange={searchContentChange}
                      
                    />
                    <Select
                     
                        defaultValue="时间正序"
                        style={{ width: 110,marginLeft:12 }}
                        options={orderTypes}
                        onChange={orderTypeChange}

                    />
                    <Search placeholder="请输入" allowClear onSearch={onSearch}  style={{ width: 240,marginLeft:12 }} />

                    <div ref={scrollContainerRef} style={{ height: '450px', overflowY: 'auto', width: '100%',marginTop:24 }}>
                    {
                        historyList.map((item,index)=>(

                            <div className='history-role-card' key={item.id}>
                                <div className='history-name-time'>
                                 
                                { searchType==2&&   <Highlight
                                        text={item.title}
                                    searchText={searchText}
                                
                                    />}
                                    {searchType == 1 && <Highlight
                                        text={item.title}
                                        searchText={''}

                                    />}

                                    <span className='story-time'>{format(new Date(item.updateTime), 'yyyy-MM-dd HH:mm:ss')}</span>

                               </div>

                                <ImageSelect tag='isSelected' options={item.actorPris.map(item => {
                                    return {
                                        ...item,
                                        isSelected: currentTag.id == item.id ? true : false,
                                        name: item.actorName,
                                        url: item.posterImage,
                                       
                                    }
                                }) || []} onSelect={item => selectType(item)} />

                            </div>
                        ))
                    }
                        {isLoading && !isEnd && <div className='loading-text'>加载中...</div>}  
                        {isEnd && (<div className='loading-text'>
                                <img src={endIcon} alt="No more items" />
                                <div>还没有历史角色呢...</div>
                            </div>)}  
                    </div> 
                 
                </div>
            </Modal>
        </>
    )
}
export default HistoryRoleLibrary