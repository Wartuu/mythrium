import { useState } from 'react';
import '../styles/navbar.scss'



const NavBar = () => {
    const [username, setUsername] = useState('Example Username')


    return (
        <>
            <div className="navbar-flex">
                <div className="navbar-container">
                    <div className="navbar-domain"></div>
                    <div className="navbar-navigation"></div>
                    <div className="navbar-account">
                        <div className="navbar-data">
                            <div className="navbar-username">
                                {username}
                            </div>
                            <a href='/dashboard'>
                                <input type='button' className='navbar-action' value='dashboard'/>
                            </a>
                        </div>
                        <img src="" alt="" className="navbar-userprofile" />
                    </div>
                </div>
            </div>
        </>
    )
}

export default NavBar;