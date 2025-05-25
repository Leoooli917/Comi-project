

const Highlight = ({ text, searchText }) => {
                if (!searchText) return <span>{text}</span>;

                const parts = text.split(new RegExp(`(${searchText})`, 'gi'));


                return (
                                <span>
                                                {parts.map((part, index) =>
                                                                part== searchText ? (
                                                                                <span className="story-name" key={index} style={{ color: '#EA0000' }}>
                                                                                                {part}
                                                                                </span>
                                                                ) : (
                                                                    <span className="story-name" key={index}>{part}</span>
                                                                )
                                                )}
                                </span>
                );
};

export default Highlight;