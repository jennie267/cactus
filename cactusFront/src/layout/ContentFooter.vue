<template>
  <footer class="footer px-4">
    <div class="row align-items-center justify-content-lg-between">
      <div class="col-lg">
        <ul class="nav nav-footer justify-content-right justify-content-lg-end">
          <li class="nav-item">
            © {{year}} <a href="#" class="font-weight-bold ml-1" target="_blank">CACTUS</a>
          </li>
        </ul>
      </div>
    </div>
  </footer>
</template>
<script>
export default {
  data() {
    return {
      year: new Date().getFullYear()
    };
  }
};
</script>
<style></style>
