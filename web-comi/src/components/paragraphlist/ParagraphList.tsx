import { Typography, Tooltip } from 'antd';
import { useState } from 'react';
import './ParagraphList.less'
function ParagraphList({ arr, paragraphChange }) {

      var [paragraphArr, setParagraphArr] = useState(arr ? arr : [])

      const [rows, setRows] = useState(2);
      const [expanded, setExpanded] = useState(false);
      const defaultSymbol = (isExpanded: boolean) => (

            <span className="ant-typography">

                  {isExpanded ? '收起' : '展开'}

            </span>
      );
      function addParagraph(event, index) {
            // console.log(event)
            let arr = [...paragraphArr]
            arr.splice(index + 1, 0, {
                  value: ''
            });
            setParagraphArr(arr)
            paragraphChange(arr)



      };
      function deleteParagraph(event, index) {
            // console.log(event)
            let arr = [...paragraphArr]
            arr.splice(index, 1);
            setParagraphArr(arr)
            paragraphChange(arr)



      };
      function updateStoryValue(value, index) {

            let arr = [...paragraphArr]
            arr[index].value = value

            setParagraphArr(arr)
            paragraphChange(arr)

      }

      return (
            <>
                  <div className='paragraph-list'>
                        {
                              paragraphArr.map((item, index) => (
                                    <div className='paragraph' key={index}>
                                          <div className='paragraph-top'>
                                                <span className='text'>{
                                                      index < 9 ? '0' + (index + 1) : (index + 1)

                                                }</span>
                                                <div className='top-right'>
                                                      <Tooltip placement="topLeft" title="添加分镜" arrow={true}>
                                                            <img className='img-style' onClick={(event) => addParagraph(event, index)} src='https://s2.loli.net/2024/04/03/bBjV75Nud43y2Yz.png'></img>
                                                      </Tooltip>
                                                      <Tooltip placement="topLeft" title="删除分镜" arrow={true}>
                                                      <img className='img-style' onClick={(event) => deleteParagraph(event, index)} src='https://s2.loli.net/2024/04/03/KHYXanBp6j2Lbsm.png'></img>
                                                      </Tooltip>
                                                      

                                                </div>
                                          </div>
                                          <div className='line'></div>

                                          <Typography.Paragraph
                                                className='paragraph-content'
                                                editable={{
                                                      onChange: (value) => { updateStoryValue(value, index) }
                                                }}

                                                ellipsis={{
                                                      rows,
                                                      expandable: 'collapsible',
                                                      expanded,
                                                      symbol: ((expanded) => defaultSymbol(expanded)),

                                                      onExpand: (event, info) => setExpanded(info.expanded),
                                                }}

                                          >
                                                {item.value}
                                          </Typography.Paragraph>

                                    </div>
                              ))
                        }
                     
                  </div >
            </>
      )

}
export default ParagraphList