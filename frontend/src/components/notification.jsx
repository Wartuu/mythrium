import PropTypes from 'prop-types'

import '../styles/notification.scss'
import '../styles/global.scss'

import React from 'react';
import { useState } from 'react';
import { useSpring, animated } from '@react-spring/web'


const Notification = (props) => {

    const animation = useSpring({
        from: {transform: 'translateX(25vw)'},
        to: {transform: 'translateX(0vw)'},
        config: {tension: 100, fraction: 20},
    })

    return (
        <animated.div className="notification-wrapper" style={animation}>
            <div className={`${props.type}-color notification-color`}/>
            <div className="notification-content">
                <div className="notification-title">{props.title}</div>
                <div className="notification-message">{props.message}</div>
            </div>
        </animated.div>
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