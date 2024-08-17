const baseUrl = 'http://api.mythrium.xyz/';

async function sendRequest(url, data, method = 'POST', options) {
	var headers = {
		'Content-Type': 'application/json'
	};

	var session = localStorage.getItem('session');
	if (session !== null) {
		headers['Authorization'] = `Bearer ${session}`;
	}

	const response = await fetch(baseUrl + url, {
		method: method,
		body: data,
		headers: {
			...headers,
			...options.headers
		},
		...options
	});

	return response.json();
}

export default sendRequest;
