import PropTypes from 'prop-types';
import '../styles/window.scss'
import { useEffect, useState } from 'react';

const Window = (props) => {

    const WINDOW_SIZE = 350;
    const WINDOW_BAR_SIZE = 25;

    const MINIMIZE_SIZE_OFFSET = 15;


    const [isDragging, setIsDragging] = useState(false);
    const [position, setPosition] = useState({x: 0, y: 0});
    const [lastPosition, setLastPosition] = useState({x: 0, y: 0});

    const [isOpen, setIsOpen] = useState(true);

    const [isMinimized, setIsMinimized] = useState('visible');
    const [windowSize, setWindowSize] = useState(WINDOW_SIZE + WINDOW_BAR_SIZE)

    const mouseDownHandler = (e) => {
        setIsDragging(true);
        setLastPosition({x: e.clientX, y: e.clientY});
    }

    const mouseUpHandler = () => {
        setIsDragging(false);
    }

    const mouseMoveHandler = (e) => {
        if(!isDragging) {
            return;
        }

        let positions = {x: position.x + lastPosition.x - e.clientX, y: position.y + lastPosition.y - e.clientY};

        positions.x = Math.min(positions.x, window.innerWidth - WINDOW_SIZE);

        if(isMinimized) {
            positions.y = Math.min(positions.y, window.innerHeight + WINDOW_BAR_SIZE + MINIMIZE_SIZE_OFFSET);
        } else {
            positions.y = Math.min(positions.y, window.innerHeight + WINDOW_BAR_SIZE + WINDOW_SIZE);
        }

        setPosition(positions);
        setLastPosition({x: e.clientX , y: e.clientY});

    }


    useEffect(() => {
        document.addEventListener('mousemove', mouseMoveHandler);
        document.addEventListener('mouseup', mouseUpHandler);

        return () => {
            document.removeEventListener('mousemove', mouseMoveHandler);
            document.removeEventListener('mouseup', mouseUpHandler);
        }
    });


    const closeWindow = () => {
        setIsOpen(false);
        setIsMinimized('hidden');
    }

    const toggleMinimize = () => {
        if(isMinimized === 'visible') {
            setIsMinimized('hidden');
            setWindowSize(WINDOW_BAR_SIZE + MINIMIZE_SIZE_OFFSET);
        
        }
        else {
            setIsMinimized('visible');
            setWindowSize(WINDOW_SIZE + WINDOW_BAR_SIZE)
        }
    }

    return isOpen ? (
        <div className="window-wrapper" style={{transform: `translate(${350 - position.x}px, ${25 - position.y}px)`, height: `${windowSize}px`}}>
            <div className="window-bar"  onMouseDown={mouseDownHandler}>
                <span className="window-title"> {props.title} </span>
                <div className="window-buttons">
                    <input type="button" value={'__'} className="window-button window-button-minimize" onClick={toggleMinimize}/>
                    <input type="button" value={'X'} className="window-button window-button-close" onClick={closeWindow}/>
                
                </div>
             
            </div>
            <div className="window-content" style={{visibility: isMinimized}}>
                {props.element}
            </div>
        </div>
    ) : null;


}


Window.propTypes = {
    title: PropTypes.string,
    element: PropTypes.any
};

export default Window;

