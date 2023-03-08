import ProductCard from "./ProductCard";
import {useEffect, useState} from "react";
import {utility} from "../utility.js";

export default function HomePage() {
    const [allProducts, setAllProducts] = useState([]);

    useEffect(() => {
        return async () => {
            await utility.apiGet(`/products/list/0`)
                .then(response => response.json())
                .then(data => {
                    setAllProducts(data.content);
                })
        };
    }, []);


    return (<div>
            <h1 className="center-text">Going out of Business!</h1>

            <div className="page-wrapper">
                <div className="sidebar-left middle-text">*Insert very creative add here.*</div>
                <div className="main-content">
                    <div>TODO MENU/SEARCH POINTS</div>
                    <div className="main-page-div">
                        {allProducts.map(data => {
                            return <div className="card-key-div" key={"key-" + data.id}><ProductCard product={data}/></div>
                        })}
                    </div>
                </div>
                <div className="sidebar-right middle-text">*Insert very creative add here.*</div>
            </div>
        </div>
    );
}