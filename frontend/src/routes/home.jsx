import  { useState, useEffect } from 'react';
import { Link } from 'react-router-dom';
import '../styles/home.scss';

const HomePage = () => {
    const MOTTO = 'Forever free and open-source'
    const [animation, setAnimation] = useState(MOTTO[0]);

    useEffect(() => {
        if (!(MOTTO.length > 0)) return;

        const intervalID = setInterval(() => {
            setAnimation(prevAnimation => {
                if (prevAnimation.length < MOTTO.length) {
                    return prevAnimation + MOTTO[prevAnimation.length];
                } else {
                    clearInterval(intervalID);
                    return prevAnimation;
                }
            });
        }, 150);

        return () => clearInterval(intervalID); // Cleanup the interval on component unmount
    }, [MOTTO]);

    
    

    return (
        <>
            <div id="page" className="home-bg">
                <Link to={'/login'} className="button-link log-in-url">
                    <button className="button-comment" id="log-in">
                        Log in
                    </button>
                </Link>

                <div className="center-flex" id="title">Mythrium</div>
                <div className="center-flex" id="motto">{animation}</div>

                <div id="cards">
                    <div id="about" className="card">
                        <div className="header">open-source</div>
                        <div className="details">
                            our project is actively developed at our <a href="https://github.com/wartuu/mythrium" target="_blank" rel="noopener noreferrer" className="href">repository</a> at github.com
                        </div>
                    </div>
                    <div id="join" className="card">
                        <div className="header">Join us!</div>
                        <div className="details">
                            Our project provides free storage for all of your notes! <br/><br/>
                            With an easy-to-use editor, you can edit, create, upload all files
                        </div>
                        <Link to={'/register'} className="button-link" id="register">
                            <button className="button-action filler-button register">
                                Join now!
                            </button>
                        </Link>
                    </div>
                    <div id="share" className="card">
                        <div className="header">Want to share?</div>
                        <div className="details">
                            You can share all of your notes and files creating a single link for everyone or just for your friends! <br/> <br/>
                            Our application supports <br/>Real-time collaboration in the editor!
                        </div>
                    </div>
                </div>
            </div>
        </>
    );
}

export default HomePage;
