import React, { Suspense } from 'react';
import { RouterProvider, createBrowserRouter } from 'react-router-dom'
import './styles/global.scss'

import Spinner from './components/spinner';
import DEBUG from './routes/debug';
import MobileFallback from './routes/mobileFallback';

const HomePage      =   React.lazy(() => import('./routes/home'))
const Error404      =   React.lazy(() => import('./routes/error404'))
const LoginPage     =   React.lazy(() => import('./routes/login'))
const RegisterPage  =   React.lazy(() => import('./routes/register'))
const Note          =   React.lazy(() => import('./routes/note'))
const AdminPanel    =   React.lazy(() => import('./routes/adminPanel'))
const Dashboard     =   React.lazy(() => import('./routes/dashboard'))


const router = createBrowserRouter([
    {
        'path': '/',
        element: <HomePage/>
    },
    {
        'path': '/login',
        element: <LoginPage/>
        
    },
    {
        'path': '/register',
        element: <RegisterPage/>
        
    },
    {
        'path': '/dashboard',
        element: <Dashboard/>
    },
    {
        'path': '/note/:uuid',
        element: <Note/>
    },
    {
        'path': '/admin/control',
        element: <AdminPanel/>
    },
    {
        'path': '/debug',
        element: <DEBUG/>
    },
    {
        'path': '*',
        element: <Error404/>
    }
])

function isMobileUser() {
    const mobileRegex = /(android|bb\d+|meego).+mobile|avantgo|bada\/|blackberry|blazer|compal|elaine|fennec|hiptop|iemobile|ip(hone|od)|iris|kindle|lge |maemo|midp|mmp|mobile.+firefox|netfront|opera m(ob|in)i|palm( os)?|phone|p(ixi|re)\/|plucker|pocket|psp|series(4|6)0|symbian|treo|up\.(browser|link)|vodafone|wap|windows ce|xda|xiino/i;
    return mobileRegex.test(navigator.userAgent);

}


const App = () => {
    return(
        (isMobileUser() ? (
            <MobileFallback/>
        ) : (
            <Suspense fallback={
                <div id="page" className="center-flex"><Spinner/></div>
            }>
                <RouterProvider router={router}/>
            </Suspense> 
        ))

    )
}

export default App;