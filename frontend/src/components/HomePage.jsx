import ProductCard from "./ProductCard";
import {useEffect, useRef, useState} from "react";
import {utility} from "../utility.js";

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


    return (
        <div>
            <h1 className="center-text">Going out of Business!</h1>

            <div className="page-wrapper">
                <div className="sidebar-left middle-text">*Insert very creative add here.*</div>
                <div className="main-content">
                    <div>TODO MENU/SEARCH POINTS</div>
                    <div className="main-page-div">
                        {allProducts.map(data => {
                            return <div className="card-key-div" key={"key-" + data.id}><ProductCard product={data}/>
                            </div>
                        })}
                        {/*TODO put them into smoll card and add some space between page numbers*/}
                        {/*TODO jump to page X*/}
                        <div className="page-container">
                            <span className="page-number" onClick={() => turnPage(currentPageCount - 1)}> &lt; </span>
                            <span className="page-number" onClick={() => turnPage(currentPageCount - 1)}>{currentPageCount <= 1 ? null : currentPageCount - 1}</span>
                            <span className="page-number" onClick={() => turnPage(currentPageCount)}>{currentPageCount}</span>
                            <span className="page-number" onClick={() => turnPage(currentPageCount + 1)}>{currentPageCount + 1 > totalPageCount.current ? null : currentPageCount + 1}</span>
                            <span className="page-number" onClick={() => turnPage(currentPageCount + 1)}> &gt; </span>
                        </div>
                    </div>
                </div>
                <div className="sidebar-right middle-text">*Insert very creative add here.*</div>
            </div>
        </div>
    );
}
