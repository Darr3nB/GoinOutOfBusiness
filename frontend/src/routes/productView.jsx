import React, {useCallback, useEffect, useState} from 'react';
import {Link, useParams} from "react-router-dom";
import {utility} from "../utility.js";
import LoadingIndicator from "../components/LoadingIndicator.jsx";

function ProductView() {
    const getProductDataFromAPI = useCallback(
        async () => {
            setIsLoading(true)
            const tempData = await utility.apiGet(`/products/product/${productId}`).then(r => r.json());
            setProductData(tempData);
            setIsLoading(false);
        },
        [],
    );

    let {productId} = useParams();
    const [productData, setProductData] = useState(null);
    const [isLoading, setIsLoading] = useState(true);

    useEffect(() => {
        getProductDataFromAPI();
    }, []);

    if (isLoading) return (
        <section className={'product-view'}>
            <LoadingIndicator/>
        </section>
    )
    const categoryName = productData.type.toUpperCase();
    const inventoryAmountClassName = productData.inventory > 10 ? "green" : productData.inventory > 0 ? "yellow" : "red";
    return (
        <section id={'product-view-box'} className={'max-page centered large-text right'}
                 style={{backgroundColor: "gray"}}>
            {/*ToDo add react lightbox for viewing images*/}
            <div id={'product-header-container'}>
                <div id={'product-name'} className={'header'}>{productData.name}</div>
                <Link to={`/category/${categoryName}`} id={'product-category'}
                      className={'small-text'}>{categoryName}</Link>
            </div>
            <div className={'left-column'}>
                <div id={'lightbox'} className={'fake-img'}></div>
                <div id={'product-buy-box'}>
                    <button id={'add-to-cart'}>Add to cart</button>
                    <span id={'product-price'} className={'right'}>{productData.price.toFixed(2)}</span>
                </div>
                <div id={'product-inventory'}
                     className={`${inventoryAmountClassName}`}>Currently {productData.inventory} in stock
                </div>
            </div>
            <div id={'product-description'} className={'right-column'}>
                {productData.description.repeat(10)}</div>

        </section>
    );
}

export default ProductView;