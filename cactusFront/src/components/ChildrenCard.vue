<template>
  <card class="card-stats" :show-footer-line="true">
    <div class="row">
      <div class="col-auto" v-if="$slots.icon || icon">
        <slot name="icon">
          <div class="icon icon-shape text-white rounded-circle shadow"
               :class="[`bg-${type}`, iconClasses]">
            <i :class="icon"></i>
          </div>
        </slot>
      </div>

      <div class="col-auto">
        <slot>
          <h3 class="card-title text-uppercase text-muted mb-0" v-if="title">{{title}}</h3>
        </slot>
      </div>


    </div>
  </card>
</template>
<script>
  import Card from './Card.vue';

  export default {
    name: 'children-card',
    components: {
      Card
    },
    props: {
      type: {
        type: String,
        default: 'primary'
      },
      icon: String,
      title: String,
      subTitle: String,
      iconClasses: [String, Array]
    }
  };
</script>
<style></style>
