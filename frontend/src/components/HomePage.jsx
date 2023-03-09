import ProductCard from "./ProductCard";
import {useEffect, useRef, useState} from "react";
import {utility} from "../utility.js";
import CheckBoxForSearch from "./CheckBoxForSearch.jsx";
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
        // TODO api request by search then display
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
                                <CheckBoxForSearch id="1" value="Console"/>
                                <CheckBoxForSearch id="2" value="Household"/>
                                <CheckBoxForSearch id="3" value="Electronics"/>
                                <CheckBoxForSearch id="4" value="Other"/>

                                {/*TODO make it as a slide*/}
                                <p>Price range:</p>
                                <label htmlFor="min-price-search-input">From: </label>
                                <input type="number" name="min-price-search-input"
                                       className="price-search-field"/>
                                <br/><label htmlFor="max-price-search-input">To: </label>
                                <input name="max-price-search-input" type="number" className="price-search-field"/>
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
                        {/*TODO jump to page X*/}
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
