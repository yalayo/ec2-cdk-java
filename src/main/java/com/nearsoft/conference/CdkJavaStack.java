package com.nearsoft.conference;

import software.amazon.awscdk.core.Construct;
import software.amazon.awscdk.core.Stack;
import software.amazon.awscdk.core.StackProps;
import software.amazon.awscdk.services.ec2.*;

import java.util.Collections;

public class CdkJavaStack extends Stack {
    public CdkJavaStack(final Construct parent, final String id) {
        this(parent, id, null);
    }

    public CdkJavaStack(final Construct parent, final String id, final StackProps props) {
        super(parent, id, props);

        final Vpc vpc = new Vpc(this, "vpc");
        final SecurityGroupProps securityGroupProps = SecurityGroupProps.builder()
                .vpc(vpc)
                .build();

        final SecurityGroup securityGroup = new SecurityGroup(this, "securityGroup", securityGroupProps);

        final UserData userData = UserData.custom("");

        final GenericLinuxImageProps genericLinuxImageProps = GenericLinuxImageProps.builder()
                .userData(userData)
                .build();

        final GenericLinuxImage genericLinuxImage = new GenericLinuxImage(Collections.emptyMap(), genericLinuxImageProps);

        final Instance instance = Instance.Builder.create(this, "jitsi-instance")
                .instanceType(new InstanceType("t2.micro"))
                .machineImage(genericLinuxImage)
                .securityGroup(securityGroup)
                .build();
    }
}
