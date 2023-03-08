import {useNavigate} from "react-router-dom";

export default function ProductCard({product}){
    // TODO request country of the user, use their money type as display
    const navigate = useNavigate();

    const redirectToProduct = (event) => {
        event.preventDefault();

        navigate(`/view-product/${product.id}`);
    }

    return (
        <div className="product-card" onClick={event => redirectToProduct(event)}>
            <p>Name: {product.name}</p>
            <p>Price: {product.price.toFixed(2)} Ft</p>
            <p>Type: {product.type}</p>
            <p>In stock: {product.inventory}</p>
        </div>
    );
}
