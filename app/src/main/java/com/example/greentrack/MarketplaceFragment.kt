package com.example.greentrack

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment

class MarketplaceFragment : Fragment() {

    private lateinit var btnBuy1: Button
    private lateinit var btnContactSeller1: Button
    private lateinit var btnBuy2: Button
    private lateinit var btnContactSeller2: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_marketplace, container, false)

        btnBuy1 = view.findViewById(R.id.btnBuy1)
        btnContactSeller1 = view.findViewById(R.id.btnContactSeller1)
        btnBuy2 = view.findViewById(R.id.btnBuy2)
        btnContactSeller2 = view.findViewById(R.id.btnContactSeller2)

        btnBuy1.setOnClickListener {
            openLink("https://www.amazon.in/Go-Hooked-Multicolor-Plastic-Beautiful/dp/B083ZCXL7K/ref=sr_1_1_sspa?adgrpid=156480577881&dib=eyJ2IjoiMSJ9.rBLJRGujkuIZO2Zr7dGToq0pmysP89XqCfgUG-SyzaL_IsFHP6A2b_aVTgiORwnG1qxkC1LCfVYOO2zOWCIKcZ-_sUJ-eVgS6VrOcoT8Amc55ZzmWTDp-1kpB8cPIpSB9ruyfA7JksXkH5t9Xsd0_JmQ-bt9-3AflGHL5azexaeJXZEcUuxYCn00rC0LPwYhFuG0vxHL937cQ7Sn2c9NMB8dqSPCG71W-EbVwXR3H5m8lbgzvqK3Q45zIY2QXFgbdcjyrlxK11J9qNYRlqolZnjDWIdy_83pxCxnWUZGE78.YrZlb-rTYiIOvgLqTu0VIzlrHVFuo4QQIQnDndn-UU8&dib_tag=se&hvadid=671661039787&hvdev=c&hvlocphy=9300861&hvnetw=g&hvqmt=b&hvrand=680133907016060340&hvtargid=kwd-446005309566&hydadcr=12666_2278328&keywords=flower%2Bpots%2Bhanging%2Bplastic&mcid=edc3f63d6d543d96a01915db6cd5ff31&qid=1762611871&sr=8-1-spons&sp_csd=d2lkZ2V0TmFtZT1zcF9hdGY&th=1")
        }

        btnContactSeller1.setOnClickListener {
            sendMail("seller@greentrack.com", "Inquiry: Recycled Plastic Pots")
        }

        btnBuy2.setOnClickListener {
            openLink("https://www.amazon.in/s?k=organic+compost+for+plants&crid=2OI57AFJCCZMJ&sprefix=organic+compos%2Caps%2C212&ref=nb_sb_ss_mvt-t11-ranker_ci_hl-bn-left_1_14")
        }

        btnContactSeller2.setOnClickListener {
            sendMail("seller@greentrack.com", "Inquiry: Organic Compost")
        }

        return view
    }

    private fun openLink(url: String) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        startActivity(intent)
    }

    private fun sendMail(email: String, subject: String) {
        val intent = Intent(Intent.ACTION_SENDTO).apply {
            data = Uri.parse("mailto:")
            putExtra(Intent.EXTRA_EMAIL, arrayOf(email))
            putExtra(Intent.EXTRA_SUBJECT, subject)
        }
        startActivity(Intent.createChooser(intent, "Contact Seller"))
    }
}
