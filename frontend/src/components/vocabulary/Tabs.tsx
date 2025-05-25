import { useState, useRef, useEffect } from 'react';
import { LeftOutlined, RightOutlined } from '@ant-design/icons';
import './Tabs.less'; // 定义自定义样式


// TabsContainer 组件
const TabsContainer = ({ items }) => {
    const [activeTab, setActiveTab] = useState(items[0].id);
    const containerRef = useRef(null);
    const [showLeftButton, setShowLeftButton] = useState(false);
    const [showRightButton, setShowRightButton] = useState(false);

    useEffect(() => {
        const handleScroll = () => {
            if (containerRef.current) {
                const { scrollLeft, scrollWidth, clientWidth } = containerRef.current;
                setShowLeftButton(scrollLeft > 0);
                setShowRightButton(scrollLeft + clientWidth < scrollWidth);
            }
        };

        handleScroll(); // Initial check
        containerRef.current.addEventListener('scroll', handleScroll);

        return () => {
            containerRef.current?.removeEventListener('scroll', handleScroll);
        };
    }, []);
    
    const handleTabClick = (tabId) => {
        setActiveTab(tabId);
    };
    const scrollLeft = () => {
        if (containerRef.current) {
            containerRef.current.scrollBy({ left: -100, behavior: 'smooth' });
        }
    };

    const scrollRight = () => {
        if (containerRef.current) {
            containerRef.current.scrollBy({ left: 100, behavior: 'smooth' });
        }
    };

    return (
        <div className="tabs-wrapper">
            <div className='tabs-container-wrapper'>
                <div className="tabs-container" ref={containerRef}>
                    <TabList tabs={items} activeTab={activeTab} onTabClick={handleTabClick} />
                </div>
                {showLeftButton && <span className="scroll-button-pre" onClick={scrollLeft} />}
                {showRightButton && <span className="scroll-button-next" style={{ right: 0 }} onClick={scrollRight} />}
            </div>

            <TabContent tabs={items} activeTab={activeTab} />
        </div>
    );
};

// TabList 组件
const TabList = ({ tabs, activeTab, onTabClick }) => {
    return (
        <ul className="tab-list">
            {tabs.map(tab => (
                <Tab
                    key={tab.id}
                    tab={tab}
                    isActive={tab.id === activeTab}
                    onClick={() => onTabClick(tab.id)}
                />
            ))}
        </ul>
    );
};

// Tab 组件
const Tab = ({ tab, isActive, onClick }) => {
    return (
        <li
            className={`tab ${isActive ? 'active' : ''}`}
            onClick={onClick}
        >
            {tab.label}
        </li>
    );
};

// TabContent 组件
const TabContent = ({ tabs, activeTab }) => {
    const activeTabContent = tabs.find(tab => tab.id === activeTab).content;

    return (
        <div className="tab-content">
            {activeTabContent}
        </div>
    );
};

export default TabsContainer;
