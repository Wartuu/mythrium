import { createRouter, createWebHistory } from 'vue-router';

const Home = () => import('@/pages/HomePage.vue');
const Register = () => import('@/pages/RegisterPage.vue');
const Login = () => import('@/pages/LoginPage.vue');
const Dashboard = () => import('@/pages/DashboardPage.vue');
const Workers = () => import('@/pages/WorkerPage.vue');
const Note = () => import('@/pages/NotePage.vue');
const NotFound = () => import('@/pages/NotFoundPage.vue');

const routes = [
	{
		path: '/',
		component: Home
	},
	{
		path: '/register',
		component: Register
	},
	{
		path: '/login',
		component: Login
	},
	{
		path: '/dashboard',
		component: Dashboard
	},
	{
		path: '/workers',
		component: Workers
	},
	{
		path: '/note/:uuid',
		component: Note
	},
	{
		path: '/:pathMatch(.*)*',
		component: NotFound
	}
];

const router = createRouter({
	history: createWebHistory(),
	routes
});

export default router;
