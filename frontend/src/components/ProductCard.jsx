import {useNavigate} from "react-router-dom";
import {utility} from "../utility.js";

export default function ProductCard({product}){
    // TODO request country of the user, use their money type as display
    const navigate = useNavigate();

    const redirectToProduct = (event) => {
        event.preventDefault();

        navigate(`/view-product/${product.id}`);
    }

    return (
        <div className="product-card white-text" onClick={event => redirectToProduct(event)}>
            <img className="product-card-picture" src={product.picture ? product.picture : utility.questionMarkPicture} alt="Picture of the product"/>
            <p><span className="bold">Name:</span> {product.name}</p>
            <p><span className="bold">Price:</span> {product.price.toFixed(2)} Ft</p>
            <p><span className="bold">Type:</span> {product.type.charAt(0).toUpperCase() + product.type.slice(1).toLowerCase()}</p>
            <p><span className="bold">In stock:</span> {product.inventory}</p>
        </div>
    );
}
