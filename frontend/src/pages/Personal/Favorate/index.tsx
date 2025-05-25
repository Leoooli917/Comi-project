import { getMyStory } from "../../../api/StoryApi";
import { Masonry } from "masonic";
import { Link, useNavigate } from 'react-router-dom';
import { useHookstate } from '@hookstate/core';
import { useEffect, useLayoutEffect } from "react";
import '../index.less'

const FakeCard = ({ data: { title, posterImage, id } }) => {
    const navigate = useNavigate();
    return (

        <div className="personal-story-item" onClick={() => window.open(`/productDetail?stroyId=${id.get()}`, "_blank")}>
            <div>
                <img style={{ width: "100%", minHeight: "135px", border: "none" }} src={posterImage.get()} alt="" />
            </div>
            <div className="personal-story-item-title" style={{ height: "47px" }}>
                {title.get()}
            </div>
        </div>

    )
};

export default function Favorite() {
    const items = useHookstate([]);
    useEffect(() => {
        // getMyStory({
        //     type: 3
        // }).then(res => {
        //     items.merge(res);
        // })
    }, [])
    return (<>
        <Masonry
            // Provides the data for our grid items
            items={items}
            // Adds 8px of space between the grid cells
            columnGutter={16}
            // Sets the minimum column width to 172px
            columnWidth={256}
            // Pre-renders 5 windows worth of content
            overscanBy={5}
            // This is the grid item component
            render={FakeCard}
        />
    </>)
}