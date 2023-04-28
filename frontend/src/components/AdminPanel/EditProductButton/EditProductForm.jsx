import React, {useContext, useRef} from 'react';
import {CategoryContext} from "../../../context.jsx";
import {utility} from "../../../utility.js";


function EditProductForm({productObject, refreshFunction}) {
    const categories = useContext(CategoryContext);

    const nameRef = useRef();
    const descriptionRef = useRef();
    const priceRef = useRef();
    const inventoryRef = useRef();
    const categoryRef = useRef();

    async function sendUpdate(event){
        event.preventDefault();
        // ToDo Add data checking
        const data = {};
        if(nameRef.current.value) {
            data.name = nameRef.current.value;
        }
        if(descriptionRef.current.value) {
            data.description = descriptionRef.current.value;
        }
        if(priceRef.current.value) {
            data.price = priceRef.current.value;
        }
        if(inventoryRef.current.value) {
            data.inventory = inventoryRef.current.value;
        }
        // if(categoryRef.current.value != null) {
        //     data.category = categoryRef.current.value;
        // }
        console.log("SENDING")
        await utility.apiAdminPut(`/admin/edit-product/${productObject.id}`, data);
        if(typeof refreshFunction === "function") {
            console.log("is func")
            setTimeout(() => refreshFunction(), 1000);
        } else {
            window.location.reload();
        }
    }


    return (
        <form onSubmit={sendUpdate}>
            <label htmlFor={'name'}>New name</label>
            <input name={'name'} type={'text'} ref={nameRef} placeholder={productObject.name}/>

            <label htmlFor={'description'}>New description</label>
            <input name={'description'} type={'text'} ref={descriptionRef} placeholder={productObject.description}/>

            <label htmlFor={'price'}>New price</label>
            <input name={'price'} type={'number'} ref={priceRef} placeholder={productObject.price}/>

            <label htmlFor={'inventory'}>New inventory</label>
            <input name={'inventory'} type={'number'} ref={inventoryRef} placeholder={productObject.inventory}/>

            {/*ToDo add category select*/}
            {/*<label htmlFor={'category'}>New category</label>*/}
            {/*<select name={'category'} ref={categoryRef}>*/}
            {/*</select>*/}
            {/*<input name={'category'} type={'number'} ref={categoryRef} placeholder={productObject.category}/>*/}

            <button type={"submit"}>Send changes</button>
        </form>
    );
}

export default EditProductForm;