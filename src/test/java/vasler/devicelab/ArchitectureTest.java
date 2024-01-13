package vasler.devicelab;

import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;
import org.jmolecules.archunit.JMoleculesArchitectureRules;
import org.jmolecules.archunit.JMoleculesDddRules;

@AnalyzeClasses(packagesOf = Application.class)
public class ArchitectureTest {

//    @ArchTest
//    public static final ArchRule myRule = classes().that().resideInAPackage("..service..").should().onlyBeAccessed().byAnyPackage("..controller..");

    @ArchTest
    public static final ArchRule ddd = JMoleculesDddRules.all();
    @ArchTest
    public static final ArchRule hexagonal = JMoleculesArchitectureRules.ensureHexagonal();
}
