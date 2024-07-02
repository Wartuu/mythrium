<script>
export default {
	props: {
		uuid: {
			type: String,
			required: true
		}
	},
	computed: {
		noteId() {
			return this.uuid;
		}
	}
};
</script>

<template>UUID: {{ $route.params.uuid }}</template>

<style scoped lang="scss"></style>
