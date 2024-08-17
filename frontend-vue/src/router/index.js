import sendRequest from '@/api/apiClient';
import {createRouter, createWebHistory} from 'vue-router';

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
		component: Dashboard,
		meta: {
			requiresAuth: true
		}
	},
	{
		path: '/workers',
		component: Workers,
		meta: {
			requiresAuth: true
		}
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

router.beforeEach(async (to, from, next) => {
	if (to.matched.some(record => record.meta.requiresAuth)) {
		next({
			path: '/login',
			query: {redirect: to.fullPath}
		});
	} else {
		next();
	}
});

export default router;
