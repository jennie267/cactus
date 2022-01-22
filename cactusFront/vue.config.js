const webpack = require('webpack');
//const isProd = process.env.NODE_ENV === "production";
const HtmlWebpackExternalsPlugin = require('html-webpack-externals-plugin');

module.exports = {
  configureWebpack: {
    // Set up all the aliases we use in our app.
    plugins: [
      new webpack.optimize.LimitChunkCountPlugin({
        maxChunks: 6
      }),
      new HtmlWebpackExternalsPlugin({
        externals: [
          {
            module: 'daum-postcode-api',
            entry: 'http://dmaps.daum.net/map_js_init/postcode.v2.js',
            global: 'daum-postcode-api',
          },
        ],
      }),
    ],
    entry: ["babel-polyfill", "./src/main.js"],
  },
  pwa: {
    name: 'Vue Argon Dashboard',
    themeColor: '#172b4d',
    msTileColor: '#172b4d',
    appleMobileWebAppCapable: 'yes',
    appleMobileWebAppStatusBarStyle: '#172b4d'
  },
  css: {
    // Enable CSS source maps.
    sourceMap: process.env.NODE_ENV !== 'production'
  },
};
