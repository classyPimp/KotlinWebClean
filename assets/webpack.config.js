const path = require('path');
const { CheckerPlugin } = require('awesome-typescript-loader')
const { TsConfigPathsPlugin } = require('awesome-typescript-loader')
const CleanWebpackPlugin = require('clean-webpack-plugin')
const ExtractTextPlugin = require('extract-text-webpack-plugin')

let jsDestinationPath = `${path.resolve(__dirname, './developmentBuild/public/assets/js')}`
let cssDestinationPath = `${path.resolve(__dirname, './developmentBuild/public/assets/css')}`

module.exports = {
  entry: {
    'js/index': './javascript/index.tsx',
    'css/index': './css/index.js',
    'js/hilfhund': './hilfhund/js/index.tsx',
    //'css/hilfhund': './hilfhund/css/index.js'
  },
  watch: true,
  watchOptions: {
    aggregateTimeout: 300,
    poll: 1000,
    ignored: /node_modules/
  },
  output: {
    filename: '[name].js',
    path: path.resolve(__dirname, './developmentBuild/public/assets/')//'../src/main/webapp/public/assets/js')
  },
  resolve: {
    extensions: ['.ts', '.tsx', '.js', '.jsx']
  },
  devtool: 'source-map',
  module: {
    loaders: [
      {
        test: /\.tsx?$/,
        loader: 'awesome-typescript-loader'
      },
      {
        test: /\.(s*)css$/,
        use: ExtractTextPlugin.extract({
          fallback: 'style-loader',
          use: ['css-loader', 'sass-loader']
        })
      }
    ]
  },
  plugins: [
    new CheckerPlugin(),
    new TsConfigPathsPlugin({ configFileName: "./tsconfig.json" }),
    new ExtractTextPlugin(
      {
        filename: 'css/index.css',
        allChunks: true
      }
    ),
    new CleanWebpackPlugin(['./developmentBuild/public/assets/js'])
  ]
};