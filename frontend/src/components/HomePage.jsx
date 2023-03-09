import ProductCard from "./ProductCard";
import {useEffect, useRef, useState} from "react";
import {utility} from "../utility.js";
import {Link} from "react-router-dom";

export default function HomePage() {
    const [allProducts, setAllProducts] = useState([]);
    const [currentPageCount, setCurrentPageCount] = useState(1);
    const totalPageCount = useRef(0);
    const categories = useRef([]);
    const [pageTurned, setPageTurned] = useState(true);

    const [filtered, setFiltered] = useState(false);

    const categoryRef = useRef();
    const searchNameRef = useRef();
    const fromRef = useRef();
    const toRef = useRef();
    const directionRef = useRef();

    const getCategories = async () => {
        const response = await utility.apiGet(`/products/get-categories`);
        categories.current = await response.json();
    }

    useEffect(() => {
        async function fetchData() {
            const response = await utility.apiGet(`/products/list/${currentPageCount - 1}`);
            const data = await response.json();
            totalPageCount.current = data.totalPages;
            setAllProducts(data.content);
        }

        getCategories();
        if (filtered) {
            sendfilterRequest();
            return;
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

        setFiltered(true);
        if (currentPageCount === 1) {
            sendfilterRequest();
        }
        setCurrentPageCount(1)
    }

    const sendfilterRequest = async () => {
        const category = categoryRef.current.value === "null" ? null : categoryRef.current.value;
        const name = searchNameRef.current.value.length <= 0 ? null : searchNameRef.current.value;
        const from = fromRef.current.value.length <= 0 ? 0 : fromRef.current.value;
        const to = toRef.current.value.length <= 1 ? Number.MAX_SAFE_INTEGER : toRef.current.value;
        const direction = directionRef.current.value;

        if (from >= to) {
            alert("Price range 'to' cannot be higher than 'from'!");
            return;
        }

        const data = await utility.apiGet(`/products/search/${currentPageCount - 1}?name=${name === null ? '' : name}&from=${from}&to=${to}&direction=${direction}${category === null ? '' : `&category=${category}`}`)
            .then(response => {
                if (response.ok) {
                    return response.json();
                }
            });
        setAllProducts(data.content);
        totalPageCount.current = data.totalPages;
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
                                <br/><input type="text" name="name-search-input" minLength="3" ref={searchNameRef}/>

                                {/*TODO get all types from backend, list proper options based on that*/}
                                <label htmlFor="category">Category: </label><br/>
                                <select name="category" id="category" ref={categoryRef}>
                                    <option value="null"></option>
                                    {categories.current?.map((cat, index) => {
                                        return <option key={`cat-key${index}`}
                                                       value={cat}>{cat.charAt(0).toUpperCase() + cat.slice(1).toLowerCase()}</option>
                                    })}
                                </select>

                                {/*TODO make it as a slide*/}
                                <p>Price range:</p>
                                <label htmlFor="min-price-search-input">From: </label>
                                <input type="number" name="min-price-search-input"
                                       className="price-search-field" ref={fromRef}/>
                                <br/><label htmlFor="max-price-search-input">To: </label>
                                <input name="max-price-search-input" type="number" className="price-search-field"
                                       ref={toRef}/>
                                <br/>
                                <label htmlFor="direction">Direction: </label>
                                <br/><select name="order-by" id="direction" ref={directionRef}>
                                <option value="desc">Descending</option>
                                <option value="asc">Ascending</option>
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
