window.onload = async () => {
    try {
        let request = await fetch("/api/appeal/count");
        if (request.ok) {
            let number = await request.json();
            let counter = document.getElementById('counter')
            counter.innerText = number;
        } else {
            throw new Error()
        }
    } catch (error) {
        console.log(error)
    }
}
