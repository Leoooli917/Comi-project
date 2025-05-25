import { SearchOutlined } from '@ant-design/icons';

import { Button, Input, Select, Space } from 'antd';
import './AfflatusContent.css'

function AfflatusContent(){
                return (
                                <>
                                                <div className='afflatus-content-body'>
                                                                <div className='afflatus-content-welcome'>欢迎来到口米小说绘图</div>
                                                                <Space.Compact className='afflatus-content-compact'>
                                                                                <Input placeholder='快把你的故事灵感告诉我吧！' />
                                                                                <Button className='afflatus-content-btn' type="primary">生成</Button>
                                                                </Space.Compact>
                                                                <div className='afflatus-content-write-well'>我的故事写好了，直接上传</div>

                                                                <div className='afflatus-content-tip'>
                                                                                1.  输入灵感后，ai智能生成，需要生成动画交互；<br/>
                                                                                2.粘贴自己已有的故事后，是否需要ai按自己的逻辑进行分场景整理后展示

                                                                </div>
                                </div>
                                </>
                )
}
export default AfflatusContent