import React from 'react'
import ReactDOM from 'react-dom/client'
import './index.css'
import {createBrowserRouter, RouterProvider} from "react-router-dom";
import Index from "./routes";
import HomePage from "./components/HomePage";
import ProductView from "./routes/productView.jsx";

const router = createBrowserRouter([
    {
        path: "/",
        element: <Index/>,
        children: [
            {
                index: true,
                element: <HomePage/>
            },
            {
                path:"/view-product/:productId",
                element: <ProductView/>
            }
        ]
    }
])

ReactDOM.createRoot(document.getElementById('root')).render(
    <React.StrictMode>
        <RouterProvider router={router}/>
    </React.StrictMode>,
)
