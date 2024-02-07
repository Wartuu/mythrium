import { Link } from 'react-router-dom';
import '../styles/404.scss'


const Error404 = () => {
    return (
        <>
            <div className="center-flex" id="page">
                Not found!

            
                <Link to={'/'} className="button-link" id="goback">
                    <button className="button-action">
                        Go back
                    </button>
                </Link>
            
            </div>



        </>

    )
}

export default Error404;
