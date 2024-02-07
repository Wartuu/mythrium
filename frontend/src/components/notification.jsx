import PropTypes from 'prop-types'

import '../styles/notification.scss'

const Notification = (props) => {
    return (
        <div className="notification-wrapper">
            <div className={`notification-color ${props.type}`}/>
            <div className="notification-content">
                <div className="notification-title">{props.title}</div>
                <div className="notification-message">{props.message}</div>
            </div>
        </div>
    );
}


Notification.propTypes = {
    type: PropTypes.string,
    title: PropTypes.string,
    message: PropTypes.string
};

const NotificationManager = () => {

    



    return (
        <div id="notification-manager">
            <Notification title={"example"} message={"this is an a example fucking test i hate tdasdasdasdsahis fucking code"}/>
            <Notification/>
            <Notification/>
        </div>
    );
}

export default NotificationManager;