import React, { Suspense } from 'react';
import { RouterProvider, createBrowserRouter } from 'react-router-dom'
import './styles/global.scss'

import Spinner from './components/spinner';
import DEBUG from './routes/debug';

const HomePage      =   React.lazy(() => import('./routes/home'))
const Error404      =   React.lazy(() => import('./routes/error404'))
const LoginPage     =   React.lazy(() => import('./routes/login'))
const RegisterPage  =   React.lazy(() => import('./routes/register'))
const Note          =   React.lazy(() => import('./routes/note'))

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
        'path': '/note/:uuid',
        element: <Note/>
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

const App = () => {
    return(
        <Suspense fallback={
            <div id="page" className="center-flex"><Spinner/></div>
        }>
            <RouterProvider router={router}/>
        </Suspense> 
    )
}

export default App;