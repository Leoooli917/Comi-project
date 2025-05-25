import { useHookstate } from '@hookstate/core';
import './index.less'
import Highlight from '../hightlight/Highlight'

type Option = {
    url: string
    name: string
    [key: string]: any
};

type Prop = {
    options: Option[]
    tag?: string
    onSelect: (option: Option) => void
};

const ImageSelect: React.FC<Prop> = ({
    options,
    tag = 'id',
    onSelect = () => { }
}) => {
    const state = useHookstate<Record<string, [] | string>>({
        itemList: [],
        actived: ''
    });

    return (<>
        <div className="img-select-container">
            {options.map((option, index) => {
                const isSelected: boolean = option[tag] == state.active;
                return (
                    <div className={isSelected ? 'img-card-selected' : 'img-card'} key={index} onClick={() => onSelect(option)}>
                        <img className={`img-card-pic ${option.isSelected ? 'pic-selected' : ''}`} src={option.url} ></img>
                        <div className='img-card-menoy'>{option.name}</div>
                       
                        {option.isSelected && (
                            <>
                                <img className='img-card-selected-bg' src='https://s2.loli.net/2024/04/08/yv59edj6DHYZwcN.png' />
                                <img className='img-card-selected-pic' src='https://s2.loli.net/2024/04/08/1aoVvxF8l69zXCD.png' />
                            </>
                        )}
                    </div>
                )
            })}
        </div>
    </>)
};

export default ImageSelect;