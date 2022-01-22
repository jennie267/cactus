<template>
    <div>
        <base-header type="gradient-success" class="">
            <div class="card-header bg-transparent row align-items-center">
                <h1 class=""><h1 class="ni ni-cart"></h1>  쇼핑  </h1>
            </div>
        </base-header>
        <div class="bg-transparent">
            <h2 style="margin-left: 5%;margin-top: 1%;"> - 한달간 인기상품  </h2>
        </div>
        <div class="products">
            <div class="row">
                <div class="col-md-3 order-md-1 mb-4">

                </div>
                <div class="col-md-9 order-md-2">
                </div>
            </div>
        </div>


        <div class="bg-transparent">
            <h2 style="margin-left: 5%;margin-top: 1%;"> - 내상품  </h2>
        </div>



        <div class="col-xl-3 col-lg-6">
            <products-card title="간장게장"
                        type="gradient-red"
                        sub-title="350,897"
                        icon="ni ni-active-40"
                        class="mb-4 mb-xl-0"
                        img="https://chenyiya.com/codepen/product-3.jpg"
                        url="https://smartstore.naver.com/matchef/products/3063746984?n_media=27758&n_query=%EA%B0%84%EC%9E%A5%EA%B2%8C%EC%9E%A5&n_rank=1&n_ad_group=grp-a001-02-000000007401464&n_ad=nad-a001-02-000000037411108&n_campaign_type=2&n_mall_pid=3063746984&NaPm=ct%3Dk3crpvkw%7Cci%3D0Ae0002OfrDr5eK5zfod%7Ctr%3Dpla%7Chk%3Dbed3989f2bff4d56bd5d40295f402f93c526950d">

            </products-card>
        </div>


    </div>
</template>
<script>
  export default {
      components: {
      },
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

      },
    methods: {
      onCopy() {
        this.$notify({
          type: 'success',
          title: 'Copied to clipboard'
        })
      }
    }
  };


</script>
<style></style>
