<template>
	<v-app id="inspire" class="my-3">
		<v-btn class="my-3" color="deep-orange accent-3" outlined rounded to="/user/selectMap">
			시작하기
		</v-btn>
			<div id="carouselExampleCaptions" class="carousel slide" data-ride="carousel">
				<ol class="carousel-indicators">
					<li data-target="#carouselExampleCaptions" data-slide-to="0" class="active"></li>
				</ol>
				<div class="carousel-inner">
					<div class="carousel-item active">
						<img src="../assets/img/1.jpg" class="d-block w-100" alt="...">
						<div class="carousel-caption d-none d-md-block">
							<h5>중요한 만남을 위한 완벽한 맛집</h5>
							<p>완벽한 맛남에서 찾아보세요.</p>
						</div>
					</div>
				</div>
				<a class="carousel-control-prev" href="#carouselExampleCaptions" role="button" data-slide="prev">
					<span class="carousel-control-prev-icon" aria-hidden="true"></span>
					<span class="sr-only">Previous</span>
				</a>
				<a class="carousel-control-next" href="#carouselExampleCaptions" role="button" data-slide="next">
					<span class="carousel-control-next-icon" aria-hidden="true"></span>
					<span class="sr-only">Next</span>
				</a>
			</div>
  </v-app>
</template>

<script>
	export default {
		name:"Main",
		
}
</script>

<style>

</style>