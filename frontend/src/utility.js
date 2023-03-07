export let utility = {
    apiGet: async function (path) {
        return await fetch(path);
    },
    apiPostWithDictionary: async function (path, data) {
        return await fetch(path, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(data)
        })
            .catch(reason => console.log(`An error happened: ${reason}`));
    },
    validateEmail: function (email) {
        const regEx = /\S+@\S+\.\S+/;

        return regEx.test(email);
    }
}