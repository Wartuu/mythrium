<script>
import sendRequest from '@/api/apiClient';

export default {
	methods: {
		async handleLogin() {

		}
	}
}
</script>

<template>
	<div class="center-flex" style="height: 800px;">
		<div class="top-flex card">
			<div class="header">Login</div>

			<input type="text" name="username" placeholder="Username" id="username">
			<input type="password" name="password" placeholder="Password" id="password">


			<input type="button" value="login" class="button-action" id="login" @click="handleLogin()">


			<div class="end-flex">
				<router-link to="/register" custom v-slot="{ navigate }">
					<a class="comment-url" style="margin: 25px; margin-bottom: 0px" @click="navigate"
						@keypress.enter="navigate">
						Login instead
					</a>
				</router-link>
				<br />
			</div>

		</div>


	</div>

</template>

<style scoped lang="scss">
@import url("@/styles/cards.scss");

.card {
	align-items: center;
}



input {
	margin: 10px;
}

#login {
	margin: 30px;
}
</style>