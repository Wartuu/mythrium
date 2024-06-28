import { createMemoryHistory, createRouter } from 'vue-router';

const Home = () => import('@/pages/HomePage.vue');

const routes = [
	{
		path: '/',
		component: Home
	}
];

const router = createRouter({
	history: createMemoryHistory(),
	routes
});

export default router;
