import ProductCard from "./ProductCard";
import {useEffect, useRef, useState} from "react";
import {utility} from "../utility.js";
import {Link} from "react-router-dom";

export default function HomePage() {
    const [allProducts, setAllProducts] = useState([]);
    const [currentPageCount, setCurrentPageCount] = useState(1);
    const totalPageCount = useRef(0);

    useEffect(() => {
        async function fetchData() {
            const response = await utility.apiGet(`/products/list/${currentPageCount - 1}`);
            const data = await response.json();
            totalPageCount.current = data.totalPages;
            setAllProducts(data.content);
        }

        fetchData();
    }, [currentPageCount]);

    const turnPage = (pageNumber) => {
        if (pageNumber === null || currentPageCount === pageNumber || pageNumber <= 0 || pageNumber > totalPageCount.current) {
            return;
        }
        setCurrentPageCount(pageNumber);
    }

    const filterItems = (event) => {
        event.preventDefault();
        const formData = new FormData(event.currentTarget);
        const category = formData.get('category') === "null" ? null : formData.get('category');
        const name = formData.get('name-search-input').length <= 0 ? null : formData.get('name-search-input');
        const from = formData.get('min-price-search-input') <= 0 ? 0 : formData.get('min-price-search-input');
        const to = formData.get('max-price-search-input') <= 1 ? Number.MAX_SAFE_INTEGER : formData.get('max-price-search-input');
        
        if (from >= to) {
            alert("Price range 'to' cannot be higher than 'from'!");
            return;
        }

        utility.apiGet(`/products/search/0?name=${name === null ? '' : name}&from=${from}&to=${to}&direction=${formData.get('order-by')}${category === null ? '' : `&category=${category}`}`)
            .then(response => {
                if (response.ok) {
                    response.json().then(data => {
                        setAllProducts(data.content);
                        // TODO update page number without triggering useEffect
                    })
                }
            });
    }


    return (
        <div>
            <h1 className="center-text">Going out of Business!</h1>

            <div className="page-wrapper">
                <div className="sidebar-left middle-text">
                    <Link to={`https://www.codecool.com`} target="_blank"><img src="../src/assets/hp_meme.jpg"
                                                                               alt="meme" className="meme"/></Link>
                </div>
                <div className="main-content">
                    <div className="search-container">
                        <div className="search-bar">
                            Filter
                            <form onSubmit={event => filterItems(event)}>
                                <label htmlFor="name-search-input">Name:</label>
                                <br/><input type="text" name="name-search-input" minLength="3"/>

                                {/*TODO get all types from backend, list proper options based on that*/}
                                <label htmlFor="category">Category: </label><br/>
                                <select name="category" id="category">
                                    <option value="null"></option>
                                    <option value="household">Household</option>
                                    <option value="console">Console</option>
                                    <option value="electronics">Electronics</option>
                                </select>

                                {/*TODO make it as a slide*/}
                                <p>Price range:</p>
                                <label htmlFor="min-price-search-input">From: </label>
                                <input type="number" name="min-price-search-input"
                                       className="price-search-field"/>
                                <br/><label htmlFor="max-price-search-input">To: </label>
                                <input name="max-price-search-input" type="number" className="price-search-field"/>
                                <br/>
                                <select name="order-by" id="order-by">
                                    <option value="asc">Ascending</option>
                                    <option value="desc">Descending</option>
                                </select>
                                <br/>
                                <button>Search</button>
                            </form>
                        </div>
                    </div>
                    <div className="main-page-div">
                        {allProducts.map(data => {
                            return <div className="card-key-div" key={"key-" + data.id}><ProductCard product={data}/>
                            </div>
                        })}
                        <div className="page-container">
                            <span className="page-number" onClick={() => turnPage(currentPageCount - 1)}> &lt; </span>
                            <span className="page-number"
                                  onClick={() => turnPage(currentPageCount - 1)}>{currentPageCount <= 1 ? null : currentPageCount - 1}</span>
                            <span className="page-number"
                                  onClick={() => turnPage(currentPageCount)}>{currentPageCount}</span>
                            <span className="page-number"
                                  onClick={() => turnPage(currentPageCount + 1)}>{currentPageCount + 1 > totalPageCount.current ? null : currentPageCount + 1}</span>
                            <span className="page-number" onClick={() => turnPage(currentPageCount + 1)}> &gt; </span>
                        </div>
                    </div>
                </div>
                <div className="sidebar-right middle-text"><Link to={`https://www.codecool.com`} target="_blank"><img
                    src="../src/assets/kermit_meme.jpg" alt="meme" className="meme"/></Link></div>
            </div>
        </div>
    );
}
