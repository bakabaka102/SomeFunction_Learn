package hn.single.server.ui.detailnews

import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import hn.single.server.base.BaseFragment
import hn.single.server.databinding.FragmentDetailNewsBinding

class DetailNewsFragment : BaseFragment<FragmentDetailNewsBinding>() {

    override fun getViewBinding() = FragmentDetailNewsBinding.inflate(layoutInflater)
    private val args: DetailNewsFragmentArgs by navArgs()

    override fun setUpViews() {
        /* val article = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
             arguments?.getParcelable("article", Article::class.java)
         } else {
             arguments?.getParcelable("article")
         }
         binding?.tvTitleDetail?.text = article?.title*/

        /*val article1 = requireArguments().getParcelable<Article>("article")
        article1?.let {
            binding?.tvTitleDetail?.text = it.title
            binding?.tvContentDetail?.text = it.description
        }*/

        val article = args.article
        binding?.apply {
            tvTitleDetail.text = article.title
            tvSourceDetail.text = article.source.name
            tvPublishedAtDetail.text = article.publishedAt
            tvContentDetail.text = article.description
            Glide.with(requireActivity()).load(article.urlToImage).into(ivThumbnail)
        }
    }
}