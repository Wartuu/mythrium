<script>
import { ref, onMounted, onUnmounted } from 'vue';

export default {
	name: 'HomePage',
	setup() {
		const MOTTO = 'Forever free and open-source';
		const animation = ref(MOTTO[0]);

		let intervalID = null;

		const animateText = () => {
			intervalID = setInterval(() => {
				if (animation.value.length < MOTTO.length) {
					animation.value += MOTTO[animation.value.length];
				} else {
					clearInterval(intervalID);
				}
			}, 150);
		};

		onMounted(() => {
			animateText();
		});

		onUnmounted(() => {
			clearInterval(intervalID);
		});

		return {
			animation
		};
	}
};
</script>

<template>
	<div class="purple-gradient-background">
		<div class="end-flex">
			<router-link to="/login" custom v-slot="{ navigate }">
				<button class="button-comment login-button" style="margin: 25px; margin-bottom: 0px" @click="navigate"
					@keypress.enter="navigate">
					Log in
				</button>
			</router-link>
			<br />
		</div>

		<div class="center-flex" id="title">Mythrium</div>

		<div class="center-flex" id="motto">
			{{ animation }}
		</div>

		<div id="cards">
			<div class="card">
				<div class="header">open-source</div>
				<div class="details">
					our project is actively developed at our
					<a href="https://github.com/wartuu/mythrium" target="_blank" rel="noopener noreferrer"
						className="href">repository</a>
					at github.com
				</div>
			</div>
			<div class="card">
				<div class="header">Join us!</div>
				<div class="details">
					Our project provides free storage for all of your notes!
					<br /><br />
					With an easy-to-use editor, you can edit, create, upload all
					files

					<div class="center-flex">
						<router-link to="/register" custom v-slot="{ navigate }">
							<button class="button-action" style="margin: 25px" @click="navigate"
								@keypress.enter="navigate">
								Log in
							</button>
						</router-link>
					</div>
				</div>
			</div>
			<div class="card">
				<div class="header">Want to share?</div>
				<div class="details">
					You can share all of your notes and files creating a single
					link for everyone or just for your friends! <br />
					<br />
					Our application supports <br />Real-time collaboration in
					the editor!
				</div>
			</div>
		</div>
	</div>
</template>

<style scoped lang="scss">
@import "@/styles/cards.scss";

#motto {
	font-size: 35px;
	font-display: bold;

	color: $gray;
	text-shadow: 2px 2px 5px $dark-shadow;

	@media (max-width: 550px) {
		font-size: 25px;
	}
}

#title {
	font-size: 100px;
	font-weight: bold;

	@media (max-width: 550px) {
		font-size: 80px;
	}
}

.purple-gradient-background {
	width: 100vw;
	height: fit-content;

	background: $background;
	background: linear-gradient(180deg, $background 60%, rgba(106, 90, 205, 1) 130%);
}
</style>
