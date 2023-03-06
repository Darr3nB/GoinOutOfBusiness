import React from 'react'
import ReactDOM from 'react-dom/client'
import './index.css'
import {createBrowserRouter, RouterProvider} from "react-router-dom";
import Index from "./routes";
import HomePage from "./components/HomePage";

const router = createBrowserRouter([
    {
        path: "/",
        element: <Index/>,
        children: [
            {
                index: true,
                element: <HomePage/>
            }
        ]
    }
])

ReactDOM.createRoot(document.getElementById('root')).render(
    <React.StrictMode>
        <RouterProvider router={router}/>
    </React.StrictMode>,
)
