<template>
  <card class="card-stats" :show-footer-line="true">

    <div class="container rounded">
      <img src="https://chenyiya.com/codepen/product-3.jpg" alt="Avatar" class="image" style="width:100%">
      <div class="hoverImg">
        <div class="text">John Doe</div>
      </div>
    </div>

    <div class="row">

      <a v-on:mouseover="mouseover" :href="url" target="_blank"><img :src="img" style="width:100%;"  class="rounded"></a>

    </div>

    <template slot="footer">
      <h2 class="card-title text-uppercase text-muted mb-0" v-if="title">{{title}}</h2>
      <span class="h2 font-weight-bold mb-0" v-if="subTitle">{{subTitle}}</span>
    </template>

  </card>
</template>
<script>
  import Card from './Card.vue';

  export default {
    name: 'products-card',
    components: {
      Card
    },
    props: {
      type: {
        type: String,
        default: 'primary'
      },
      icon: String,
      img: String,
      url: String,
      title: String,
      subTitle: String,
      iconClasses: [String, Array]
    },
    methods: {
      mouseover: function(){
      }

    }
  };
</script>
<style>

  .image {
    opacity: 1;
    display: block;
    width: 100%;
    height: auto;
    transition: .5s ease;
    backface-visibility: hidden;
  }

  .hoverImg {
    transition: .5s ease;
    opacity: 0;
    position: absolute;
    top: 50%;
    left: 50%;
    transform: translate(-50%, -50%);
    -ms-transform: translate(-50%, -50%);
    text-align: center;
  }

  .container:hover .image {
    opacity: 0.3;
  }

  .container:hover .hoverImg {
    opacity: 1;
  }

  .text {
    background-color: #4CAF50;
    color: white;
    font-size: 16px;
    padding: 16px 32px;
  }

</style>
