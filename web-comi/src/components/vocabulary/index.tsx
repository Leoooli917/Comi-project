import { useEffect, useState, useRef } from 'react'
import { Modal } from 'antd';
import Tabs from './Tabs';
import { formatPromptInput } from '@/utils/formatPromptInput';
import { getTagsByType } from '@/api/TagsApi';
import './index.less'



function VocabularyModal({ title, isModalVisible, handleModalCancel, prompt, onCancelChoose, onChoose, targetRef }) {
    const [vocabularyList, setVocabularyList] = useState([])
    const [tagList, setTagList] = useState([])
    const modalRef = useRef(null);
    const modalWidth = 573;
    const [modalOffsetRight, setModalOffsetRight] = useState(0) // modal向右偏移的距离


    useEffect(() => {
        if (!prompt) {
            setTagList([])
            return
        }
        const formatPrompt = formatPromptInput(prompt);
        setTagList(formatPrompt)
    }, [prompt])

    const handleTagClick = (tag) => {
        if (tagList.includes(tag.name) || tagList.includes(tag.name_en)) {
            onCancelChoose(tag.name, tag.name_en);
        } else {
            onChoose(tag.name);
        }
    };

    useEffect(() => {
        if (!vocabularyList || vocabularyList.length == 0) {
            let type = 1;
            switch (title) {
                case '人物描述词库':
                    type = 1;
                    break;
                case '角色词库':
                    type = 2;
                    break;
                case '环境描述词库':
                    type = 3;
                    break;
                default:
                    break;
            }
            getTagsByType({ type: type })
                .then(data => {
                    const tabsData = []
                    data.list.forEach(element => {
                        const list = []
                        element.list.forEach(item => {
                            const tag = {
                                key: item.id, // tag
                                name: item.name,
                                name_en: item.nameEn
                            }
                            list.push(tag)
                        });
                        const tab = {
                            key: element.id,
                            label: element.name,
                            list: list,
                        }
                        tabsData.push(tab)
                    })
                    // console.log('tabsData', tabsData);
                    setVocabularyList(tabsData)
                })
        }
    }, [])

    useEffect(() => {
        if (targetRef.current && document.body) {
            const targetRect = targetRef.current.getBoundingClientRect();
            const bodyRect = document.body.getBoundingClientRect();
            const left = (bodyRect.width - modalWidth) / 2 - targetRect.width
            setModalOffsetRight(Math.floor(left))
        }
    }, [isModalVisible]);


    return (
        <>
            <Modal
                title={title}
                open={isModalVisible}
                onCancel={handleModalCancel}
                mask={false}
                footer={null}
                width={modalWidth}
                centered
                style={{ right: -modalOffsetRight }}
            >
                <Tabs
                    items={vocabularyList.map(block => {
                        return {
                            label: block.label,
                            id: block.key,
                            content: <ul className="recommend__list">
                                {block.list.map(tag => (
                                    <li
                                        key={tag.key}
                                        className={`recommend__item ${tagList.includes(tag.name) || tagList.includes(tag.name_en) ? 'is-active' : ''}`}
                                        onClick={() => handleTagClick(tag)}
                                    >
                                        <p className="recommend__item__name">{tag.name}</p>
                                        <p className="recommend__item__en">{tag.name_en}</p>
                                    </li>
                                ))}
                            </ul>,
                        }
                    })}>
                </Tabs>
            </Modal>
        </>
    )
}
export default VocabularyModal