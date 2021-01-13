package org.granitesoft.math.bigdecimal;

import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.MathContext;
import java.math.RoundingMode;

import org.granitesoft.math.ExpField;
import org.junit.Test;

public class TestBigDecimalField {
    private static final MathContext MC = new MathContext(100, RoundingMode.HALF_EVEN);
    static final BigDecimalField hpf = new BigDecimalField(MC);

    boolean isEq(BigDecimal x, BigDecimal y) {
        return x.subtract(y).abs().compareTo(x.ulp()) <= 0;
    }
    

    boolean same(BigDecimal d, BigDecimal d2) {
        return isEq(d.round(MC), d2.round(MC));
    }

    public static BigDecimal PI = new BigDecimal(
            "3.14159265358979323846264338327950288419716939937510582097494459230781640628620899862803482534211706798214808651328230664709384460955058223172535940812848111745028410270193852110555964462294895493038196");

    @Test
    public void testPi() {
        assertTrue(same(PI, hpf.pi()));
    }

    @Test
    public void testE() {
        assertTrue(same(new BigDecimal(
                "2.71828182845904523536028747135266249775724709369995957496696762772407663035354759457138217852516642743"),
                hpf.e()));
    }

    @Test
    public void testValueOf() {
        assertTrue(same(BigDecimal.valueOf(.5), hpf.valueOf(BigDecimal.valueOf(.5))));
        assertTrue(same(BigDecimal.valueOf(5), hpf.valueOf(BigInteger.valueOf(5))));
        assertTrue(same(BigDecimal.valueOf(5), hpf.valueOf(5)));
    }

    @Test
    public void testExp() {
        assertTrue(same(hpf.valueOf(
                "1.648721270700128146848650787814163571653776100710148011575079311640661021194215608632776520056366643"),
                hpf.exp(hpf.valueOf(.5))));
        assertTrue(same(hpf.valueOf(
                "2.718279110178575916776234775534750616385800794667057828284978303023762678481264593707589706180563378975"),
                hpf.exp(hpf.valueOf(".999999"))));
        assertTrue(same(hpf.valueOf(
                "2.718281828459045235360287471352662497757247093699959574966967627724076630353547594571382178525166427"),
                hpf.exp(hpf.valueOf(1))));
        assertTrue(same(hpf.valueOf(
                "2.718309011413244370283697365530026192625196004157835252872722677896683191993171499824074154506634498"),
                hpf.exp(hpf.valueOf(1.00001))));
        assertTrue(same(hpf.valueOf(
                "485165195.4097902779691068305415405586846389889448472543536108003159779961427097401659798506527473494478"),
                hpf.exp(hpf.valueOf(20))));
        assertTrue(same(hpf.valueOf(
                "0.00000000206115362243855782796594038015582097637580727559910369297224466162916402378455935327991092791"),
                hpf.exp(hpf.valueOf(-20))));
        assertTrue(same(hpf.valueOf(
                "0.606530659712633423603799534991180453441918135487186955682892158735056519413748423998647611507989456"),
                hpf.exp(hpf.valueOf(-.5))));
        assertTrue(same(hpf.valueOf(
                "1.0000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000"),
                hpf.exp(hpf.valueOf(0))));
        assertTrue(same(hpf.valueOf(
                "1.000010000050000166667083334166668055557539685019844025575947974286518040859924541627171360141414809"),
                hpf.exp(hpf.valueOf(1e-5))));
        assertTrue(same(hpf.valueOf(
                "27.84817199565123133208979685156019326863260552600597765601316906292816980963536249641588701989850459"),
                hpf.exp(hpf.valueOf(332676732672e-11))));
    }
    @Test
    public void testExpLarge() {
        ExpField<BigDecimal> h3 = new BigDecimalField(new MathContext(103));
        BigDecimal valueOf = h3.valueOf(1e5);
        BigDecimal exp = h3.exp(valueOf);
        assertTrue(same(h3.valueOf(
                "2.806663360426123179318385818571742708536366270565886545387436534257865267048701852662923156291401488e+43429"),
                exp));
    }

    @Test
    public void testLog() {
        assertThrows(ArithmeticException.class, () -> hpf.log(hpf.valueOf(-1)));
        assertThrows(ArithmeticException.class, () -> hpf.log(hpf.valueOf(0)));
        assertTrue(same(hpf.valueOf(
                "-0.693147180559945309417232121458176568075500134360255254120680009493393621969694715605863326996418687542001481020570685734"),
                hpf.log(hpf.valueOf(.5))));
        assertTrue(same(hpf.valueOf(
                "-1.00000500003333358333533335000014285839286825406825487735321075513446942613614863667437693586192082942E-5"),
                hpf.log(hpf.valueOf(.99999))));
        assertTrue(same(hpf.valueOf(
                "0.0000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000"),
                hpf.log(hpf.valueOf(1))));
        assertTrue(same(
                hpf.valueOf("9.999999999999999999999999999999999999999999999999999999999999999999999999995E-76"),
                hpf.log(hpf.valueOf("1.000000000000000000000000000000000000000000000000000000000000000000000000001"))));
        assertTrue(same(
                hpf.valueOf("-1.00000000000000000000000000000000000000000000000000000000000000000000000000005E-76"),
                hpf.log(hpf.valueOf(".9999999999999999999999999999999999999999999999999999999999999999999999999999"))));
        assertTrue(same(hpf.valueOf(
                "3.91202300542814605861875078791055184712670284289729069794597579244175159738501024486613108318277790914"),
                hpf.log(hpf.valueOf(50))));
        assertTrue(same(hpf.valueOf(
                "62.4624671248020534695370904849310045086699702533014749920916616644579773694315936181447076420574016306"),
                hpf.log(hpf.valueOf(1.34e27))));
        assertTrue(same(hpf.valueOf(
                "-61.8771278968764134674344480680246627017895101326522657137080449877909435531454403145991414327809064798"),
                hpf.log(hpf.valueOf(1.34e-27))));
    }

    @Test
    public void testSqrt() {
        assertThrows(ArithmeticException.class, () -> hpf.sqrt(hpf.valueOf(-1)));
        assertTrue(same(hpf.valueOf(
                "0.0000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000"),
                hpf.sqrt(hpf.valueOf(0))));
        assertTrue(same(hpf.valueOf(
                "0.707106781186547524400844362104849039284835937688474036588339868995366239231053519425193767163820786368"),
                hpf.sqrt(hpf.valueOf(.5))));
        assertTrue(same(hpf.valueOf(
                "1.0000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000"),
                hpf.sqrt(hpf.valueOf(1))));
        assertTrue(same(hpf.valueOf(
                "31622776601683.7933199889354443271853371955513932521682685750485279259443863923822134424810837930029519"),
                hpf.sqrt(hpf.valueOf(1e27))));
        assertTrue(same(hpf.valueOf(
                "1.0000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000E-50"),
                hpf.sqrt(hpf.valueOf(1e-100))));
    }

    @Test
    public void testBasic() {
        assertTrue(same(hpf.valueOf(
                "2.33333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333"),
                hpf.divide(hpf.valueOf(7), hpf.valueOf(3))));
        assertTrue(same(hpf.valueOf(
                "4.0000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000"),
                hpf.subtract(hpf.valueOf(7), hpf.valueOf(3))));
        assertTrue(same(hpf.valueOf(
                "10.0000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000"),
                hpf.add(hpf.valueOf(7), hpf.valueOf(3))));
        assertTrue(same(hpf.valueOf(
                "21.0000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000"),
                hpf.multiply(hpf.valueOf(7), hpf.valueOf(3))));
    }

    @Test
    public void testAtan() {
        assertTrue(same(hpf.valueOf(
                "0.463647609000806116214256231461214402028537054286120263810933088720197864165741705300600283984887892557"),
                hpf.atan(hpf.valueOf(.5))));
        assertTrue(same(hpf.valueOf(
                "-0.982793723247329067985710611014666014496877453631628556761425088317988071549796035389706534372817311108"),
                hpf.atan(hpf.valueOf(-1.5))));
        assertTrue(same(hpf.valueOf(
                "-1.373400766945015860861271926444961148650999595899700808969783355912874233164860713581319584633770489878446"),
                hpf.atan(hpf.valueOf(-5))));
        assertTrue(same(hpf.valueOf(
                "1.373400766945015860861271926444961148650999595899700808969783355912874233164860713581319584633770489878446"),
                hpf.atan(hpf.valueOf(5))));
        assertTrue(same(hpf.valueOf(
                "-0.463647609000806116214256231461214402028537054286120263810933088720197864165741705300600283984887892557"),
                hpf.atan(hpf.valueOf(-.5))));
        assertTrue(same(hpf.valueOf(
                "0.0000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000"),
                hpf.atan(hpf.valueOf(0))));
        assertTrue(same(hpf.valueOf(
                "0.785398163397448309615660845819875721049292349843776455243736148076954101571552249657008706335529266996"),
                hpf.atan(hpf.valueOf(1))));
        assertTrue(same(hpf.valueOf(
                "0.982793723247329067985710611014666014496877453631628556761425088317988071549796035389706534372817311108"),
                hpf.atan(hpf.valueOf(1.5))));
        assertTrue(same(hpf.valueOf(
                "1.55079899282174608617056849473815495414935150100104442658157785335429926004545602862955825756335181276"),
                hpf.atan(hpf.valueOf(50))));
        assertTrue(same(hpf.valueOf(
                "9.99999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999967E-51"),
                hpf.atan(hpf.valueOf(1e-50))));
        assertTrue(same(hpf.valueOf(
                "1.57079632679489661923132169163975144209858469968754291048747229615390820314310449931401741267105853399"),
                hpf.atan(hpf.valueOf(1e50))));
    }

    @Test
    public void testAsin() {
        assertTrue(same(hpf.valueOf(
                "0.523598775598298873077107230546583814032861566562517636829157432051302734381034833104672470890352844664"),
                hpf.asin(hpf.valueOf(.5))));
        assertTrue(same(hpf.valueOf(
                "-0.523598775598298873077107230546583814032861566562517636829157432051302734381034833104672470890352844664"),
                hpf.asin(hpf.valueOf(-.5))));
        assertTrue(same(hpf.valueOf(
                "0.0000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000"),
                hpf.asin(hpf.valueOf(0))));
        assertTrue(same(hpf.valueOf(
                "1.57079632679489661923132169163975144209858469968755291048747229615390820314310449931401741267105853399"),
                hpf.asin(hpf.valueOf(1))));
        assertTrue(same(hpf.valueOf(
                "-1.57079632679489661923132169163975144209858469968755291048747229615390820314310449931401741267105853399"),
                hpf.asin(hpf.valueOf(-1))));
    }

    @Test
    public void testAcos() {
        assertTrue(same(hpf.valueOf(
                "1.04719755119659774615421446109316762806572313312503527365831486410260546876206966620934494178070568933"),
                hpf.acos(hpf.valueOf(.5))));
        assertTrue(same(hpf.valueOf(
                "1.57079632679489661923132169163975144209858469968755291048747229615390820314310449931401741267105853399"),
                hpf.acos(hpf.valueOf(0))));
        assertTrue(same(hpf.valueOf(
                "0.0000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000"),
                hpf.acos(hpf.valueOf(1))));
        assertTrue(same(hpf.valueOf(PI), hpf.acos(hpf.valueOf(-1))));
    }

    @Test
    public void testAtan2() {
        assertThrows(ArithmeticException.class, () -> hpf.atan2(hpf.valueOf(0), hpf.valueOf(0)));
        assertTrue(same(hpf.valueOf(
                "0.982793723247329067985710611014666014496877453631628556761425088317988071549796035389706534372817311108"),
                hpf.atan2(hpf.valueOf(3), hpf.valueOf(2))));
        assertTrue(same(hpf.valueOf(
                "2.158798930342464170476932772264836869700291945743477264213519503989828334736412963238328290969299756874"),
                hpf.atan2(hpf.valueOf(3), hpf.valueOf(-2))));
        assertTrue(same(hpf.valueOf(
                "-2.15879893034246417047693277226483686970029194574347726421351950398982833473641296323832829096929975687"),
                hpf.atan2(hpf.valueOf(-3), hpf.valueOf(-2))));
        assertTrue(same(hpf.valueOf(
                "-0.98279372324732906798571061101466601449687745363162855676142508831798807154979603538970653437281731111"),
                hpf.atan2(hpf.valueOf(-3), hpf.valueOf(2))));
        assertTrue(same(hpf.valueOf(
                "0.0000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000"),
                hpf.atan2(hpf.valueOf(0), hpf.valueOf(2))));
        assertTrue(same(hpf.valueOf(PI), hpf.atan2(hpf.valueOf(0), hpf.valueOf(-2))));
        assertTrue(same(hpf.valueOf(
                "1.570796326794896619231321691639751442098584699687552910487472296153908203143104499314017412671058533991074"),
                hpf.atan2(hpf.valueOf(1), hpf.valueOf(0))));
        assertTrue(same(hpf.valueOf(
                "-1.57079632679489661923132169163975144209858469968755291048747229615390820314310449931401741267105853399107"),
                hpf.atan2(hpf.valueOf(-1), hpf.valueOf(0))));
    }

    @Test
    public void testCos() {
        assertTrue(same(hpf.valueOf(
                "0.877582561890372716116281582603829651991645197109744052997610868315950763274213947405794184084682258356"),
                hpf.cos(hpf.valueOf(-.5))));
        assertTrue(same(hpf.valueOf(
                "0.877582561890372716116281582603829651991645197109744052997610868315950763274213947405794184084682258356"),
                hpf.cos(hpf.valueOf(.5))));
        assertTrue(same(hpf.valueOf(
                "1.0000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000"),
                hpf.cos(hpf.valueOf(0))));
        assertTrue(same(hpf.valueOf(
                "0.707106781186547524400844362104849039284835937688474036588339868995366239231053519425193767163820786368"),
                hpf.cos(hpf.divide(PI, BigDecimal.valueOf(4)))));
        assertTrue(same(hpf.valueOf(
                ".5000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000"),
                hpf.cos(new BigDecimal(
                        "1.047197551196597746154214461093167628065723133125035273658314864102605468762069666209344941780705689327383"))));
        assertTrue(same(hpf.valueOf(
                "-0.500000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000"),
                hpf.cos(new BigDecimal(
                        "2.094395102393195492308428922186335256131446266250070547316629728205210937524139332418689883561411378654765"))));
        assertTrue(same(hpf.valueOf(
                "-1.000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000"),
                hpf.cos(PI)));
        assertTrue(same(hpf.valueOf(
                "-1.000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000"),
                hpf.cos(PI.negate())));
        assertTrue(same(hpf.valueOf(
                "1.000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000"),
                hpf.cos(PI.multiply(BigDecimal.valueOf(2)))));
        assertTrue(same(hpf.valueOf(
                "1.000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000"),
                hpf.cos(PI.multiply(BigDecimal.valueOf(4)))));
        assertTrue(same(hpf.valueOf(
                "1.000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000"),
                hpf.cos(PI.multiply(BigDecimal.valueOf(10000)))));
    }

    @Test
    public void testSin() {
        assertTrue(same(hpf.valueOf(
                "-0.479425538604203000273287935215571388081803367940600675188616613125535000287814832209631274684348269086"),
                hpf.sin(hpf.valueOf(-.5))));
        assertTrue(same(hpf.valueOf(
                "0.479425538604203000273287935215571388081803367940600675188616613125535000287814832209631274684348269086"),
                hpf.sin(hpf.valueOf(.5))));
        assertTrue(same(hpf.valueOf(
                "0.000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000"),
                hpf.sin(hpf.valueOf(0))));
        assertTrue(same(hpf.valueOf(
                "0.707106781186547524400844362104849039284835937688474036588339868995366239231053519425193767163820786368"),
                hpf.sin(hpf.divide(PI, BigDecimal.valueOf(4)))));
        assertTrue(same(hpf.valueOf(
                "0.86602540378443864676372317075293618347140262690519031402790348972596650845440001854057309337862428783781322295548963374046933177924104"),
                hpf.sin(new BigDecimal(
                        "1.047197551196597746154214461093167628065723133125035273658314864102605468762069666209344941780705689327383"))));
        assertTrue(same(hpf.valueOf(
                "0.866025403784438646763723170752936183471402626905190314027903489725966508454400018540573093378624287838"),
                hpf.sin(new BigDecimal(
                        "2.094395102393195492308428922186335256131446266250070547316629728205210937524139332418689883561411378654765"))));
        assertTrue(same(hpf.valueOf(
                "0"),
                hpf.sin(PI)));
        assertTrue(same(hpf.valueOf(
                "0"),
                hpf.sin(PI.multiply(BigDecimal.valueOf(3)))));
    }

    @Test
    public void testTan() {
        assertTrue(same(hpf.valueOf(
                "0.546302489843790513255179465780285383297551720179791246164091385932907510518025815715180648270656218589"),
                hpf.tan(hpf.valueOf(.5))));
        assertTrue(same(hpf.valueOf(
                "0.000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000"),
                hpf.tan(hpf.valueOf(0))));
        assertTrue(same(hpf.valueOf(
                "1.000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000"),
                hpf.tan(hpf.divide(hpf.pi(), BigDecimal.valueOf(4)))));
        assertTrue(same(hpf.valueOf(
                "1.73205080756887729352744634150587236694280525381038062805580697945193301690880003708114618675724857568"),
                hpf.tan(new BigDecimal(
                        "1.047197551196597746154214461093167628065723133125035273658314864102605468762069666209344941780705689327383"))));
        assertTrue(same(hpf.valueOf(
                "-1.73205080756887729352744634150587236694280525381038062805580697945193301690880003708114618675724857568"),
                hpf.tan(new BigDecimal(
                        "2.094395102393195492308428922186335256131446266250070547316629728205210937524139332418689883561411378654765"))));
        assertTrue(same(hpf.valueOf(
                "0.000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000"),
                hpf.tan(PI)));
    }

    @Test
    public void testCosh() {
        assertTrue(same(hpf.valueOf(
                "1.12762596520638078522622516140267201254784711809866748362898573518785877030398201631571206578217804952"),
                hpf.cosh(hpf.valueOf(.5))));
    }

    @Test
    public void testSinh() {
        assertTrue(same(hpf.valueOf(
                "0.521095305493747361622425626411491559105928982611480527946093576452802250890233592317064454274188593488"),
                hpf.sinh(hpf.valueOf(.5))));
    }

    @Test
    public void testTanh() {
        assertTrue(same(hpf.valueOf(
                "0.462117157260009758502318483643672548730289280330113038552731815838080906140409278774949064151962490584"),
                hpf.tanh(hpf.valueOf(.5))));
    }

    @Test
    public void testPow() {
        assertThrows(ArithmeticException.class, () -> hpf.pow(hpf.valueOf(0), hpf.valueOf(-1)));
        assertTrue(same(hpf.valueOf(
                "4.000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000"),
                hpf.pow(hpf.valueOf(-2), hpf.valueOf(2))));
        assertTrue(same(hpf.valueOf(
                "-8.000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000"),
                hpf.pow(hpf.valueOf(-2), hpf.valueOf(3))));
        assertThrows(ArithmeticException.class, () -> hpf.pow(hpf.valueOf(-2), hpf.valueOf(.5)));
        assertTrue(same(hpf.valueOf(
                "1.000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000"),
                hpf.pow(hpf.valueOf(0), hpf.valueOf(0))));
        assertTrue(same(hpf.valueOf(
                "0.000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000"),
                hpf.pow(hpf.valueOf(0), hpf.valueOf(1))));
        assertTrue(same(hpf.valueOf(
                "1.000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000"),
                hpf.pow(hpf.valueOf(1), hpf.valueOf(0))));
        assertTrue(same(hpf.valueOf(
                "1.000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000"),
                hpf.pow(hpf.valueOf(1), hpf.valueOf(1))));
        assertTrue(same(hpf.valueOf(
                "1.000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000"),
                hpf.pow(hpf.valueOf(3), hpf.valueOf(0))));
        assertTrue(same(hpf.valueOf(
                "5.19615242270663188058233902451761710082841576143114188416742093835579905072640011124343856027174572703"),
                hpf.pow(hpf.valueOf(3), hpf.valueOf(1.5))));
        assertTrue(same(hpf.valueOf(
                "0.000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000"),
                hpf.pow(hpf.valueOf(0), hpf.valueOf(1.5))));
        assertTrue(same(hpf.valueOf(
                "0.192450089729875254836382926833985818549200583756708958672867442161325890767644448564571798528583175075"),
                hpf.pow(hpf.valueOf(3), hpf.valueOf(-1.5))));
        assertTrue(same(hpf.valueOf(
                "13872638.1676260481929070724410597174703814321078134318477537235118715165956413217201909876728766261796"),
                hpf.pow(hpf.valueOf("192450089729875"), hpf.valueOf(.5))));
    }

    @Test
    public void testAsinh() {
        assertTrue(same(hpf.valueOf(
                "0.000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000"),
                hpf.asinh(hpf.valueOf(0))));
        assertTrue(same(hpf.valueOf(
                "-0.481211825059603447497758913424368423135184334385660519661018168840163867608221774412009429122723474997"),
                hpf.asinh(hpf.valueOf(-.5))));
        assertTrue(same(hpf.valueOf(
                "0.481211825059603447497758913424368423135184334385660519661018168840163867608221774412009429122723474997"),
                hpf.asinh(hpf.valueOf(.5))));
        assertTrue(same(hpf.valueOf(
                "1.194763217287109304111930828519090523536162075153005429270680299461324095830962530268871614289314375488077"),
                hpf.asinh(hpf.valueOf(1.5))));
    }

    @Test
    public void testAcosh() {
        assertTrue(same(hpf.valueOf(
                "0.000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000"),
                hpf.acosh(hpf.valueOf(1.0))));
        assertTrue(same(hpf.valueOf(
                "0.962423650119206894995517826848736846270368668771321039322036337680327735216443548824018858245446949995"),
                hpf.acosh(hpf.valueOf(1.5))));
    }

    @Test
    public void testAtanh() {
        assertTrue(same(hpf.valueOf(
                "0.000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000"),
                hpf.atanh(hpf.valueOf(0))));
        assertTrue(same(hpf.valueOf(
                "0.549306144334054845697622618461262852323745278911374725867347166818747146609304483436807877406866044394"),
                hpf.atanh(hpf.valueOf(.5))));
        assertTrue(same(hpf.valueOf(
                "-0.549306144334054845697622618461262852323745278911374725867347166818747146609304483436807877406866044394"),
                hpf.atanh(hpf.valueOf(-.5))));
        assertTrue(same(hpf.valueOf(
                "37.18793507818470359899647933567891310565537388524049524359358642022160856582248704157888694493178209640915"),
                hpf.atanh(hpf.valueOf(".99999999999999999999999999999999"))));
    }

    @Test
    public void testReaminder() {
        assertTrue(same(hpf.valueOf(
                "1.000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000"),
                hpf.remainder(hpf.valueOf(7), hpf.valueOf(3))));
        assertTrue(same(hpf.valueOf(
                "-2.000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000"),
                hpf.remainder(hpf.valueOf(7), hpf.valueOf(-3))));
        assertTrue(same(hpf.valueOf(
                "2.000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000"),
                hpf.remainder(hpf.valueOf(-7), hpf.valueOf(3))));
        assertTrue(same(hpf.valueOf(
                "-1.000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000"),
                hpf.remainder(hpf.valueOf(-7), hpf.valueOf(-3))));
    }

    @Test
    public void testQuotient() {
        assertTrue(same(hpf.valueOf(
                "1.0000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000"),
                hpf.quotient(hpf.valueOf(3.0), hpf.valueOf(2.0))));
        assertTrue(same(hpf.valueOf(
                "-2.0000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000"),
                hpf.quotient(hpf.valueOf(-3.0), hpf.valueOf(2.0))));
        assertTrue(same(hpf.valueOf(
                "1.0000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000"),
                hpf.quotient(hpf.valueOf(-3.0), hpf.valueOf(-2.0))));
        assertTrue(same(hpf.valueOf(
                "-2.0000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000"),
                hpf.quotient(hpf.valueOf(3.0), hpf.valueOf(-2.0))));
    }

    @Test
    public void testCeil() {
        assertTrue(same(hpf.valueOf(
                "2.0000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000"),
                hpf.ceil(hpf.valueOf(2.0))));
        assertTrue(same(hpf.valueOf(
                "-2.0000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000"),
                hpf.ceil(hpf.valueOf(-2.0))));
        assertTrue(same(hpf.valueOf(
                "2.0000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000"),
                hpf.ceil(hpf.valueOf(1.3))));
        assertTrue(same(hpf.valueOf(
                "-1.0000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000"),
                hpf.ceil(hpf.valueOf(-1.3))));
    }

    @Test
    public void testFloor() {
        assertTrue(same(hpf.valueOf(
                "2.0000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000"),
                hpf.floor(hpf.valueOf(2.0))));
        assertTrue(same(hpf.valueOf(
                "-2.0000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000"),
                hpf.floor(hpf.valueOf(-2.0))));
        assertTrue(same(hpf.valueOf(
                "1.0000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000"),
                hpf.floor(hpf.valueOf(1.3))));
        assertTrue(same(hpf.valueOf(
                "-2.0000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000"),
                hpf.floor(hpf.valueOf(-1.3))));
    }
}
