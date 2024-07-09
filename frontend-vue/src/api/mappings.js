// URL's ending with '/' contains most propably a parameters!

export const ACCOUNT_URLS = {
	genNewOTP: '/api/v1/account/security/otp/new',
	confirmOTP: '/api/v1/account/security/otp/confirm',

	register: '/api/v1/account/register',
	login: '/api/v1/account/login',
	account: '/api/v1/account' // ! depracated
};

export const NOTE_URLS = {
	upload: '/api/v1/note/upload',
	get: '/api/v1/note/get/',
	search: '/api/v1/note/search/',
	list: '/api/v1/note/list'
};

export const PROXY_URLS = {
	info: '/api/v1/proxy'
};
