import PropTypes from 'prop-types'

import '../styles/notification.scss'
import '../styles/global.scss'

import React, { useEffect } from 'react';
import { useState } from 'react';
import { useSpring, animated } from '@react-spring/web'


const Notification = (props) => {

    const [isVisible, setIsVisible] = useState(true);
    const [fillerWidth, setFillerWidth] = useState('100%');
    const [exists, setExists] = useState(true);

    const [hideTimeoutStartTime, setHideTimeoutStartTime] = useState(Date.now());
    const [hideTimeoutDuration, setHideTimeoutDuration] = useState(5000);
    const [hideTimeoutRemaining, setHideTimeoutRemaining] = useState(hideTimeoutDuration);

    const [paused, setPaused] = useState(false);


    let hideTimeout;

    const animation = useSpring({

        maxHeight: isVisible ? '10vh' : '0',
        height: isVisible ? '10vh' : '0',

        from: {
            transform: isVisible ? 'translateX(30vw)' : 'translateX(0vw)'
        },
        to: {
            transform: isVisible ? 'translateX(0vw)' : 'translateX(30vw)'
        },

        config: {
            tension: 100,
            fraction: 20
        },

        onRest: () => {
            if(isVisible && !paused) {
                setHideTimeoutStartTime(Date.now());
                hideTimeout = setTimeout(() => {
                    setIsVisible(false)
                }, 5000);
            }
            if(!isVisible) {
                setExists(false);
            }
        }
    })

    const mouseEnterHandler = () => {
        setHideTimeoutStartTime(undefined);
        setHideTimeoutRemaining(5000);
        setPaused(true);
        clearTimeout(hideTimeout);
    }

    const mouseLeaveHandler = () => {
        setHideTimeoutStartTime(Date.now());
        setHideTimeoutRemaining(5000);
        setPaused(false);
        hideTimeout = setTimeout(() => {
            setIsVisible(false);
        }, 5000);
    }

    useEffect(() => {
        if (!exists) {
            return () => {
                clearInterval(intervalId);
            };
        }
    
        const intervalId = setInterval(() => {
            if(hideTimeoutStartTime === undefined) {
                return;
            }

            const elapsedTime = Date.now() - hideTimeoutStartTime;
            const remainingTime = Math.max(0, hideTimeoutDuration - elapsedTime);
            setHideTimeoutRemaining(remainingTime);
            setFillerWidth((remainingTime / hideTimeoutDuration) * 100 + '%');
            if(remainingTime === 0) {
                clearInterval(intervalId);
            }
        }, 100);
    
        return () => {
            clearInterval(intervalId);
        };
    }, [exists, hideTimeoutDuration, hideTimeoutStartTime]);

    return (

        exists ? (

            <animated.div className="notification-wrapper" style={animation} onMouseEnter={mouseEnterHandler} onMouseLeave={mouseLeaveHandler}>
                <div className={`${props.type}-color notification-color`}/>
                <div className="horizontal">
                    <div className="notification-content">
                        <div className="notification-title">{props.title}</div>
                        <div className="notification-message">{props.message}</div>
                    </div>
                    <div className="notification-bar">
                        <div className="notification-bar-filler" style={{width: fillerWidth}}/>
                    </div>
                </div>


            </animated.div>
            
        ) : null
    );
}


Notification.propTypes = {
    type: PropTypes.string,
    title: PropTypes.string,
    message: PropTypes.string
};

const NotificationManager = () => {

    var socket = new WebSocket('ws://' + window.location.host + '/ws/v1/notification');

    var [notifications, setNotifications] = useState([]);

    const showNotification = (json) => {
        var notification = <Notification title={json.title} message={json.message} type={json.type}/>
        setNotifications((prev) => [...prev, notification]);
    }

    socket.addEventListener('message', e => {
        showNotification(e.data);
    })

    const testNotification = () => {
        showNotification({title: 'testing', message: 'example message', type: 'success'});
        console.table(notifications);
    }


    return (
        <>
            <button onClick={testNotification}>test</button>
            <div id="notification-manager">
                {notifications.map((notification, index) => (
                    <React.Fragment key={index}>{notification}</React.Fragment>
                ))}
            </div>
        </>
    );
}

export default NotificationManager;