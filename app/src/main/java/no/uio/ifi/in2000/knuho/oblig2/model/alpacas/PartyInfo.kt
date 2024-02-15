package no.uio.ifi.in2000.knuho.oblig2.model.alpacas


data class Parties(
    val parties: List<PartyInfo>
)

data class PartyInfo(
    val id : String,
    val name : String,
    val leader : String,
    val img : String,
    val color : String,
    val description : String
)