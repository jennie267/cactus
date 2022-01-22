<template>
  <div id="app">

    <div id="product">
<!--      asdasdassdads
      <div v-for="(item, index) in items"  :key="item.id + index">
        <span>{{item.id + index}}</span>
      </div>-->
    </div>
    <div id="cart">
      <div id="head">
        <h3>Shopping Cart</h3>
        <div id="price">Price</div>
        <div id="quantity">Quantity</div>
        <div id="total">Total</div>
      </div>
<!--      <buyitem v-for="buyitem in buyitems" v-bind:buy_data="buyitem" v-bind:key="buyitem"></buyitem>
      <h5 v-if="total()">Sum: $ {{total()}}</h5>-->
    </div>
  </div>
<!--
  <template id="product-box">
    <div class="box">
      <img :src="item_data.img"/>
      <i class="fa fa-plus" v-on:click="addItem(item_data)"></i>
      <h2>{{item_data.title}}</h2>
      <p>$ {{item_data.price}}</p>
    </div>
  </template>

  <template id="buy-box">
    <div class="row">
      <img :src="buy_data.img"/>
      <h4>{{buy_data.title}}</h4>
      <p>$ {{buy_data.price}}</p>
      <div class="qty-minus" v-on:click="minusQty(buy_data)">-</div>
      <div class="qty">{{buy_data.qty}}</div>
      <div class="qty-plus" v-on:click="plusQty(buy_data)">+</div>
      <div class="del" v-on:click="removeItem(buy_data)">Remove</div>
      <div class="totalprice">{{buy_data.total}}</div>
    </div>
  </template>-->

</template>


<script>
  import "@/assets/scss/argon.scss";

  export default {
    name: 'projects-table',
    data() {
      return {
        mItems: [
          {
            img: "https://chenyiya.com/codepen/product-1.jpg",
            title: "Beer Bottle",
            price: "2500",
            id: "홍삼"
          },
          {
            img: "https://chenyiya.com/codepen/product-2.jpg",
            title: "Eco Bag",
            price: "2500",
            id: "영양제"
          },
          {
            img: "https://chenyiya.com/codepen/product-3.jpg",
            title: "Paper Bag",
            price: "2500",
            id: "사과"
          }
        ],
        buyitems: []
      }

    }
  }
</script>
<!--

<script>

  import Vue from 'vue'

  var beerClick = 0;
  var ecoClick = 0;
  var paperClick = 0;


  Vue.component("item", {
    template: "#product-box",
    props: ["item_data", "buyitems"],
    methods: {
      addItem: function(item_data) {
        if (item_data.id == "beer") {
          beerClick += 1;
          if (beerClick <= 1) {
            this.pushData();
          } else {
            var i1 = this.findIndex(this.$parent.buyitems, "id", "beer");
            this.$parent.buyitems[i1].qty += 1;
            this.$parent.buyitems[i1].total = this.$parent.buyitems[i1].qty*this.$parent.buyitems[i1].price;
          }
        } else if (item_data.id == "eco-bag") {
          ecoClick += 1;
          if (ecoClick <= 1) {
            this.pushData();
          } else {
            var i2 = this.findIndex(this.$parent.buyitems, "id", "eco-bag");
            this.$parent.buyitems[i2].qty += 1;
            this.$parent.buyitems[i2].total =this.$parent.buyitems[i2].qty*this.$parent.buyitems[i2].price;
          }
        } else {
          paperClick += 1;
          if (paperClick <= 1) {
            this.pushData();
          } else {
            var i3 = this.findIndex(this.$parent.buyitems, "id", "paper-bag");
            this.$parent.buyitems[i3].qty += 1;
            this.$parent.buyitems[i3].total = this.$parent.buyitems[i3].qty*this.$parent.buyitems[i3].price;
          }
        }
      },
      pushData: function() {
        this.$parent.buyitems.push({
          img: this.item_data.img,
          title: this.item_data.title,
          price: this.item_data.price,
          qty: 1,
          total: this.item_data.price,
          id: this.item_data.id
        });
      },
      findIndex: function(array, attr, value) {
        for (var i = 0; i < array.length; i += 1) {
          if (array[i][attr] === value) {
            return i;
          }
        }
        return -1;
      },
    }
  });
  Vue.component("buyitem", {
    template: "#buy-box",
    props: ["buy_data", "buyitems"],
    methods: {
      removeItem: function(buy_data) {
        var index = this.$parent.buyitems.indexOf(buy_data);
        this.$parent.buyitems.splice(index, 1);
        if (buy_data.id == "beer") {
          beerClick = 0;
        } else if (buy_data.id == "eco-bag") {
          ecoClick = 0;
        } else {
          paperClick = 0;
        }
      },
      plusQty: function(buy_data){
        buy_data.qty += 1;
        buy_data.total = buy_data.qty*buy_data.price;
      },
      minusQty: function(buy_data){
        buy_data.qty -= 1;
        if (buy_data.qty < 0){
          buy_data.qty = 0;
        }
        buy_data.total = buy_data.qty*buy_data.price;
      }

    }
  });

  export default {
    data() {
      return {
        items: [
          {
            img: "https://chenyiya.com/codepen/product-1.jpg",
            title: "Beer Bottle",
            price: "25",
            id: "beer"
          },
          {
            img: "https://chenyiya.com/codepen/product-2.jpg",
            title: "Eco Bag",
            price: "73",
            id: "eco-bag"
          },
          {
            img: "https://chenyiya.com/codepen/product-3.jpg",
            title: "Paper Bag",
            price: "35",
            id: "paper-bag"
          }
        ],
        buyitems: []
      }

    }
  }
</script>

<style scoped>

</style>
-->
