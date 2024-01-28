package vasler.devicelabtest;

import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;
import com.tngtech.archunit.lang.syntax.ArchRuleDefinition;
import com.tngtech.archunit.library.Architectures;
import org.jmolecules.archunit.JMoleculesArchitectureRules;
import org.jmolecules.archunit.JMoleculesDddRules;
import vasler.devicelab.Application;

@AnalyzeClasses(packagesOf = Application.class)
public class ArchitectureTest {

//    @ArchTest
//    public static final ArchRule myRule = classes().that().resideInAPackage("..service..").should().onlyBeAccessed().byAnyPackage("..controller..");

    @ArchTest
    public static final ArchRule ddd = JMoleculesDddRules.all();
    @ArchTest
    public static final ArchRule hexagonal = JMoleculesArchitectureRules.ensureHexagonal();

    @ArchTest
    public static final ArchRule gettersOnModelCalledOnlyFromDomainServices = ensureGettersOnModelCalledOnlyFromDomainServices();

    private static ArchRule ensureGettersOnModelCalledOnlyFromDomainServices() {
        return ArchRuleDefinition
            .methods()
            .that()
            .areDeclaredInClassesThat()
            .resideInAnyPackage("..domain.model..")
            .and().haveNameStartingWith("get")
            .and().doNotHaveName("getId")
            .should().onlyBeCalled().byClassesThat().resideInAnyPackage("..domain.service..");
    }
}
