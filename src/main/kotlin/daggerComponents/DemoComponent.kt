//package daggerComponents
//
//import dagger.Component
//import dagger.Module
//import dagger.Provides
//import dagger.Subcomponent
//import javax.inject.Inject
//import javax.inject.Singleton
//import java.lang.annotation.RetentionPolicy
//import javax.inject.Scope
//
//
///**
// * Created by Муса on 04.10.2017.
// */
//@Singleton
//class Bar
//@Singleton
//class Foo
//class FooBar
//@Inject constructor(val bar: Bar)
//class BarFoo
//@Inject constructor(val foo: Foo)
//class Fragment
//class FragmentUser(val fragment: Fragment)
//
//@Module
//class FooModule(var fragment: Fragment) {
//    @Singleton
//    @Provides fun provideFoo(): Foo {
//        return Foo()
//    }
//
//    @Provides fun provideFragmentUser(): FragmentUser {
//        return FragmentUser(fragment)
//    }
//
//    @Provides fun provideFooBar(bar: Bar): FooBar {
//        return FooBar(bar)
//    }
//
//}
//@Module
//class BarModule {
//    @Singleton
//    @Provides fun provideBar(): Bar {
//        return Bar()
//    }
//
//    @Provides fun provideBarFoo(foo: Foo): BarFoo {
//        return BarFoo(foo)
//    }
//}
//
//@Singleton
//@Component(
//    modules = arrayOf(
//        BarModule::class, FooModule::class
//    )
//)
//interface DemoComponent {
//    fun foo(): Foo
//    fun bar(): Bar
//    fun fooBar(): FooBar
//    fun barFoo(): BarFoo
//    fun fragmentuser(): FragmentUser
//
//    fun sessionComponent(): SessionComponent.Builder
//
//}
//
//class SessionFoo
//class SessionBar(val sessionFoo: SessionFoo, val fooBar: FooBar)
//
//@Scope
//annotation class Session
//
//@Session
//@Module
//class SessionModule {
//    @Session
//    @Provides fun sessionFoo(): SessionFoo {
//        return SessionFoo()
//    }
//
//    @Provides fun sessionBar(sessionFoo: SessionFoo, fooBar: FooBar): SessionBar {
//        return SessionBar(sessionFoo, fooBar)
//    }
//}
//
//@Session
//@Subcomponent(
//        modules = arrayOf(SessionModule::class)
//)
//interface SessionComponent {
//    @Subcomponent.Builder
//    interface Builder {
//        fun requestModule(module: SessionModule): Builder
//        fun build(): SessionComponent
//    }
//
//    fun sessionBar(): SessionBar
//    fun sessionFoo(): SessionFoo
//}
