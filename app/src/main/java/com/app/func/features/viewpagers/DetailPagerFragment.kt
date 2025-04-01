package com.app.func.features.viewpagers

import android.graphics.Color
import android.graphics.Typeface
import android.text.Editable
import android.text.TextWatcher
import android.view.MotionEvent
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.CheckBox
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.app.func.base_content.BaseFragment
import com.app.func.databinding.FragmentTabViewpagerBinding
import com.app.func.features.viewpagers.model.RowData
import com.app.func.utils.Logger
import com.app.func.utils.MyToast

class DetailPagerFragment(
    val rowData: List<RowData>,
) : BaseFragment<FragmentTabViewpagerBinding>() {

    private val dataWhenChange = mutableMapOf<String, Any>()
    private var isSpannerSelectable = false

    override fun getViewBinding() = FragmentTabViewpagerBinding.inflate(layoutInflater)

    override fun setUpViews() {
        addDynamicRow()
    }

    private fun addDynamicRow() {
        rowData.forEach {
            val titleView = createTitle(it.titleItem)
            val contentView = createContentData()
            val itemView = createParentRowLayout(titleView, contentView)
            when (it.content) {
                is String -> {
                    val editText = EditText(requireContext()).apply {
                        this.setText(it.content)
                        this.addTextChangedListener(object : TextWatcher {
                            override fun beforeTextChanged(
                                s: CharSequence?,
                                start: Int,
                                count: Int,
                                after: Int
                            ) {
                                Logger.d("beforeTextChanged: $s")
                            }

                            override fun onTextChanged(
                                s: CharSequence?,
                                start: Int,
                                before: Int,
                                count: Int
                            ) {
                                Logger.d("onTextChanged: $s")
                            }

                            override fun afterTextChanged(s: Editable?) {
                                Logger.d("afterTextChanged: $s")
                                MyToast.showToast(requireContext(), "afterTextChanged: $s")
                                dataWhenChange[it.titleItem] = s.toString()
                            }
                        })
                    }
                    contentView.addView(editText)
                }

                is Boolean -> {
                    val checkBox = CheckBox(requireContext())
                    checkBox.isChecked = it.content
                    checkBox.setOnCheckedChangeListener { _, isChecked ->
                        // Handle checkbox change
                        MyToast.showToast(requireContext(), "Selected: $isChecked")
                        dataWhenChange[it.titleItem] = isChecked
                    }
                    contentView.addView(checkBox)
                }

                is List<*> -> {
                    Spinner(requireContext()).apply {
                        val adapter = ArrayAdapter(
                            requireContext(),
                            android.R.layout.simple_spinner_item,
                            it.content
                        )
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                        this.adapter = adapter

                        // Whenever the spinner is clicked, activate what onItemSelectedListener has to do.
                        // This way we prevent undesired triggers from setSelection without touching the spinner.
                        this.setOnTouchListener { view, event ->
                            view.performClick()
                            if (event.action == MotionEvent.ACTION_UP) {
                                isSpannerSelectable = true
                            }
                            false
                        }

                        this.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                            override fun onItemSelected(
                                parent: AdapterView<*>,
                                view: View,
                                position: Int,
                                id: Long
                            ) {
                                if (isSpannerSelectable) {
                                    MyToast.showToast(
                                        requireContext(),
                                        "Selected: ${it.content[position]}"
                                    )
                                    dataWhenChange[it.titleItem] = it.content[position].toString()
                                }
                            }

                            override fun onNothingSelected(parent: AdapterView<*>) {
                                // Handle no selection
                            }
                        }
                        this.setSelection(it.selectedPosition)
                        contentView.addView(this)
                    }
                }
            }
            binding?.container?.addView(itemView)
        }
    }

    private fun createTitle(text: String): TextView {
        return TextView(context).apply {
            this.id = View.generateViewId()
            this.layoutParams = ConstraintLayout.LayoutParams(
                ConstraintLayout.LayoutParams.WRAP_CONTENT,
                ConstraintLayout.LayoutParams.WRAP_CONTENT
            ).apply {
                startToStart = ConstraintLayout.LayoutParams.PARENT_ID
                topToTop = ConstraintLayout.LayoutParams.PARENT_ID
                bottomToBottom = ConstraintLayout.LayoutParams.PARENT_ID
                layoutParams = this
            }
            this.text = text
            this.textSize = 16f
            this.setTextColor(Color.BLACK)
            this.setTypeface(null, Typeface.BOLD)
        }
    }

    private fun createContentData(): ConstraintLayout {
        return ConstraintLayout(requireContext()).apply {
            id = View.generateViewId()
            layoutParams = ConstraintLayout.LayoutParams(
                ConstraintLayout.LayoutParams.WRAP_CONTENT,
                ConstraintLayout.LayoutParams.WRAP_CONTENT
            ).apply {
                topToTop = ConstraintLayout.LayoutParams.PARENT_ID
                bottomToBottom = ConstraintLayout.LayoutParams.PARENT_ID
                endToEnd = ConstraintLayout.LayoutParams.PARENT_ID
                layoutParams = this
            }
        }
    }

    private fun createParentRowLayout(
        titleView: TextView,
        contentView: ConstraintLayout,
    ): ConstraintLayout {
        return ConstraintLayout(requireContext()).apply {
            layoutParams = ConstraintLayout.LayoutParams(
                ConstraintLayout.LayoutParams.MATCH_PARENT,
                ConstraintLayout.LayoutParams.WRAP_CONTENT
            ).apply {
                setMargins(10, 20, 10, 20)
                addView(titleView)
                addView(contentView)
            }
        }
    }

    override fun observeView() {

    }

    override fun observeData() {

    }

    override fun initActions() {

    }

    companion object {
        fun newFragment(rowData: List<RowData>) = DetailPagerFragment(rowData)
    }
}





