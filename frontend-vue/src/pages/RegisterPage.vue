<script setup></script>

<template>
	<div class="center-flex" style="height: 800px;">
		<div class="top-flex card">
			<div class="header">Register</div>

			<input type="email" name="email" placeholder="Email" id="email">
			<input type="text" name="username" placeholder="Username" id="username">
			<input type="password" name="password" placeholder="Password" id="password">


			<input type="button" value="register" class="button-action" id="register">


			<div class="end-flex">
				<router-link to="/login" custom v-slot="{ navigate }">
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

#register {
	margin: 30px;
}
</style>
