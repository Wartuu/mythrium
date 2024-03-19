import NavBar from '../components/navbar';
import NotificationManager from '../components/notification';
import Window from '../components/window'

const DEBUG = () => {

    return (
        <>
            <NavBar/>
            <Window title={'1'} element={'test content'}/>
            <NotificationManager/>
        </>
    )
}

// eslint-disable-next-line react-refresh/only-export-components
export default DEBUG;